package it.academy.builder.utilitys.tree;


import java.util.HashMap;
import java.util.Map;

public class TreeEntities {

 private final Map<String, TreeNode> treeEntities = new HashMap<>();

 public void addElement(String key, TreeNode value){
         treeEntities.put(key, value);
 }

 public boolean containsEntity(String key){
     return treeEntities.containsKey(key);
 }

 public void includeInQuery(String key){
     TreeNode treeNode = treeEntities.get(key);
     treeNode.setIncludeInQuery(true);
     treeEntities.put(key, treeNode);
 }

 public TreeNode getNode(String key){
        return treeEntities.get(key);
    }

    public Map<String, TreeNode> getTreeEntities() {
        return treeEntities;
    }
}
