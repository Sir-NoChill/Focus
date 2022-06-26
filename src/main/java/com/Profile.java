package com;

import com.exceptions.LeafAddChildException;
import elementStructure.Element;
import elementStructure.material.Reading;
import elementStructure.tasks.Project;
import elementStructure.tasks.SuperList;

import java.util.Stack;

public class Profile {
    private Stack<Element> elements;
    private int exp;

    public Profile() {
        this.elements = new Stack<>();
        this.exp = 0;

    }

    public static Profile getTestState() {

        Profile profile = new Profile();

        Element superList = new SuperList();
        superList.setTitle("To Do");

        Element project1 = new Project();
        project1.setProgress(10);
        Element project2 = new Project();
        project2.setProgress(20.0);
        Element project3 = new Project();
        Element project4 = new Project();

        Element reading1 = new Reading();
        Element reading2 = new Reading();
        Element reading3 = new Reading();
        Element reading4 = new Reading();

        project1.setTitle("p1");
        project2.setTitle("p2");
        project3.setTitle("p3");
        project4.setTitle("p4");

        reading1.setTitle("r1");
        reading1.setProgress(50);
        reading2.setTitle("r2");
        reading3.setTitle("r3");
        reading4.setTitle("r4");

        try {

            project1.addChildren(project2);
            project1.addChildren(project3);
            project2.addChildren(project4);

            project1.addChildren(reading1);
            project2.addChildren(reading2);
            project3.addChildren(reading3);
            project4.addChildren(reading4);

            superList.addChildren(project1);

        } catch (LeafAddChildException e) {
            //pass
        }
        profile.elements.add(superList);
        return profile;
    }

    public void complete(Element element) {
        this.exp += element.getRemainingExp();
        element.complete();
    }

    public void unComplete(Element element) {
        this.exp -= element.getRemainingExp();
        element.unComplete();
    }

    public Stack<Element> getElements() {
        return elements;
    }

    public int getExp() {
        return exp;
    }
}
