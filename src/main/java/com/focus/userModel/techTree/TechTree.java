package com.focus.userModel.techTree;

import annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;

public class TechTree {
    private Collection<TechNode> tree;

    public TechTree(TechNode root) {
        this.tree = new ArrayList<>();
        this.tree.add(root);
    }

    public void addNode(int expRequirement, String reward, String description) {
        this.tree.add(new TechNode(expRequirement,reward,description));
    }

    public void removeNode(TechNode techNode) {
        this.tree.remove(techNode);
    }



    public static class TechNode {
        private @Nullable Collection<TechNode> children;
        private int expRequirement;
        private String reward;
        private String description;

        public TechNode(int expRequirement, String reward, String description) {
            this.expRequirement = expRequirement;
            this.reward = reward;
            this.description = description;
        }

    }
}
