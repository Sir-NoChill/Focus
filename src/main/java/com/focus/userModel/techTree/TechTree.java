package com.focus.userModel.techTree;

import exceptions.InvalidTechNameException;

import java.util.Collection;
import java.util.Objects;

public class TechTree {
    private Collection<TechNode> tree;

    public TechTree(Collection<TechNode> root) {
        this.tree = root;
    }

    //TODO add a method in here to increment name values for the same rewards added multiple times
    public void addNode(int expRequirement, String reward, String description) {
        this.tree.add(new TechNode(expRequirement,reward,description));
    }

    public void removeNode(TechNode techNode) {
        this.tree.remove(techNode);
    }

    public TechNode searchTechNode(String name) throws InvalidTechNameException {
        for (TechNode techNode : this.tree) {
            if (Objects.equals(techNode.getReward(),name)) {
                return techNode;
            }
        }
        throw new InvalidTechNameException();
    }
}
