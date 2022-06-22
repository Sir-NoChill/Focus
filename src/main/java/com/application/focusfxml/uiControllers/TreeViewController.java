package com.application.focusfxml.uiControllers;

public class TreeViewController extends MasterController {
//
//    public TreeViewController() {
//        this.taskTreeView = buildFileSystemBrowser();
//    }
//
//    private TreeView<File> buildFileSystemBrowser() {
//        TreeItem<File> root = createNode(new File("C:/Users/aa/deaProjects"));
//        return new TreeView(root);
//    }
//
//    private TreeItem<File> createNode(final File file) {
//        return new TreeItem<File>(file) {
//            private boolean isLeaf;
//            //This could be done more dynamically, for when things are added after the instantiation of the tree
//            private boolean isFirstTimeChildren = true;
//            private boolean isFirstTimeLeaf = true;
//
//            @Override public ObservableList<TreeItem<File>> getChildren() {
//                if (isFirstTimeChildren) {
//                    isFirstTimeChildren = false;
//
//                    super.getChildren().setAll(buildChildren(this));
//                }
//                return super.getChildren();
//            }
//
//            @Override public boolean isLeaf() {
//                if (isFirstTimeLeaf) {
//                    isFirstTimeLeaf = false;
//                    File file = (File) getValue();
//                    isLeaf = file.isFile();
//                }
//                return isLeaf;
//            }
//
//            private ObservableList<TreeItem<File>> buildChildren(TreeItem<File> treeItem) {
//                File file = treeItem.getValue();
//                if (file != null && file.isDirectory()) {
//                    File[] files = file.listFiles();
//                    if (files != null) {
//                        ObservableList<TreeItem<File>> children = FXCollections.observableArrayList();
//
//                        for (File childFile : files) {
//                            children.add(createNode(childFile));
//                        }
//                        return children;
//                    }
//                }
//                return FXCollections.emptyObservableList();
//            }
//        };
//    }
//
//    public void setTreeView(TreeView<File> treeView) {
//        this.taskTreeView = treeView;
//    }
//
//    public TreeView<File> getTaskTreeView() {
//        return taskTreeView;
//    }
}
