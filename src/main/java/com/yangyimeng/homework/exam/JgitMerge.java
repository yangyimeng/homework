package com.yangyimeng.homework.exam;


import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.MergeResult;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.*;
import org.eclipse.jgit.merge.ResolveMerger;
import org.eclipse.jgit.merge.StrategyRecursive;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JgitMerge {

    public void doMerge(String gitDir, String base, List<String> mergeBranchList) throws IOException, GitAPIException{

        Repository repository = new FileRepository(gitDir);
        Git  git = new Git(repository);

        RevWalk revWalk = new RevWalk(repository);
        RevCommit baseCommit = revWalk.parseCommit(repository.getRefDatabase().getRef(base).getObjectId());
        List<RevCommit> mergeCommitList = new ArrayList<>();
        for (String mergeBranch : mergeBranchList) {
            mergeCommitList.add(revWalk.parseCommit(repository.getRefDatabase().getRef(mergeBranch).getObjectId()));
        }

        ObjectInserter objectInserter = repository.newObjectInserter();
        for (RevCommit commit : mergeCommitList) {
            MergeResult result = git.merge().include(commit).call();
            System.out.println("hello world");
        }

//        RevCommit tipCommit = baseCommit;
//        Map<String, String> failMap = new HashMap<>();
//        for (int i = 0; i < mergeCommitList.size(); i++) {
//            RevCommit commit = mergeCommitList.get(i);
//            ResolveMerger resolveMerger = (ResolveMerger) StrategyRecursive.RECURSIVE.newMerger(repository, true);
//            if (resolveMerger.merge(tipCommit, commit)) {
//                tipCommit = writeMergeCommit(resolveMerger.getResultTreeId(), tipCommit, commit, revWalk, objectInserter, mergeBranchList.get(i), "target", new PersonIdent(repository));
//            } else {
//                failMap.put(mergeBranchList.get(i), resolveMerger.getUnmergedPaths().toString());
//            }
//        }
//
//        System.out.println("Hello world");
    }

    private RevCommit writeMergeCommit(
            final ObjectId treeId,
            final RevCommit parent1,
            final RevCommit parent2,
            final RevWalk rw,
            final ObjectInserter inserter,
            final String source,
            final String targetBranch,
            final PersonIdent personIdent) throws IOException {
        String message;
        if (null == targetBranch) {
            message = String.format("Merge branch '%s'", source);
        } else {
            message = String.format("Merge branch '%s' into '%s'", source, targetBranch);
        }
        final CommitBuilder mergeCommit = new CommitBuilder();
        mergeCommit.setTreeId(treeId);
        mergeCommit.setParentIds(parent1, parent2);
        mergeCommit.setAuthor(personIdent);
        mergeCommit.setCommitter(personIdent);
        mergeCommit.setMessage(message);
        ObjectId id = inserter.insert(mergeCommit);
        inserter.flush();
        RevCommit mergeResult = rw.parseCommit(id);
        return mergeResult;
    }

    public static void main(String [] args) throws IOException, GitAPIException{
        JgitMerge jgitMerge = new JgitMerge();
        List<String> mergeBranchList = new ArrayList<>();
        mergeBranchList.add("origin/domesticFlight");
        mergeBranchList.add("origin/ivr-message-apply");
        jgitMerge.doMerge("/tmp/csc-center/.git", "origin/master", mergeBranchList);
    }

}
