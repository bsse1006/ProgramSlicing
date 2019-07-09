package dataDependence;

import graph.GraphNode;
import org.eclipse.jdt.core.dom.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class DataDependenceGraph {

    CompilationUnit compilationUnit;
    GraphNode root;
    Stack<GraphNode> graphNodeStack = new Stack<GraphNode> ();
    Map<IVariableBinding,Set<GraphNode>> map = new HashMap<>();
    int marking = 0;
    IVariableBinding s;
    Set<IVariableBinding> nameSet = new HashSet<>();

    public DataDependenceGraph(CompilationUnit compilationUnit) {
        this.compilationUnit = compilationUnit;
    }

    public GraphNode getDataDependenceGraph() {

        root = new GraphNode();
        graphNodeStack.push(root);

        compilationUnit.accept(new ASTVisitor() {
            public void preVisit (ASTNode node) {

            }

            public boolean visit (IfStatement node) {
                //System.out.println(node.getExpression());
                //System.out.println(node);
                GraphNode temp;
                temp = new GraphNode(node);
                graphNodeStack.push(temp);
                return true;
            }

            public boolean visit (SynchronizedStatement node) {
                //System.out.println(node.getExpression());
                GraphNode temp;
                temp = new GraphNode(node);
                graphNodeStack.push(temp);
                return true;
            }

            public boolean visit (ForStatement node) {
                //System.out.println(node.getExpression());
                GraphNode temp;
                temp = new GraphNode(node);
                graphNodeStack.push(temp);
                return true;
            }

            public boolean visit (EnhancedForStatement node) {
                //System.out.println(node.getExpression());
                GraphNode temp;
                temp = new GraphNode(node);
                graphNodeStack.push(temp);
                return true;
            }

            public boolean visit (WhileStatement node) {
                //System.out.println(node);
                GraphNode temp;
                temp = new GraphNode(node);
                graphNodeStack.push(temp);
                return true;
            }

            public boolean visit (DoStatement node) {
                //System.out.println(node);
                GraphNode temp;
                temp = new GraphNode(node);
                graphNodeStack.push(temp);
                return true;
            }

            public boolean visit (SwitchStatement node) {
                //System.out.println(node.getExpression());
                GraphNode temp;
                temp = new GraphNode(node);
                graphNodeStack.push(temp);
                return true;
            }

            public boolean visit (SwitchCase node) {
                //System.out.println(node.getExpression());
                GraphNode temp;
                temp = new GraphNode(node);
                graphNodeStack.push(temp);
                return true;
            }

            public boolean visit (LabeledStatement node) {
                //System.out.println(node.getExpression());
                GraphNode temp;
                temp = new GraphNode(node);
                graphNodeStack.push(temp);
                return true;
            }

            public boolean visit (TryStatement node) {

                /*List<StructuralPropertyDescriptor> list = node.structuralPropertiesForType();
                for(StructuralPropertyDescriptor a : list)
                {
                    System.out.println(a.getId());
                }*/
                GraphNode temp;
                temp = new GraphNode(node);
                graphNodeStack.push(temp);
                return true;
            }

            public boolean visit (CatchClause node) {
                //System.out.println(node.getExpression());
                GraphNode temp;
                temp = new GraphNode(node);
                graphNodeStack.push(temp);
                return true;
            }

            public boolean visit (ExpressionStatement node) {
                //System.out.println(node);
                GraphNode temp;
                temp = new GraphNode(node);
                graphNodeStack.push(temp);
                return true;
            }

            public void endVisit (ForStatement node) {
                graphNodeStack.pop();
            }

            public void endVisit (EnhancedForStatement node) {
                graphNodeStack.pop();
            }

            public void endVisit (WhileStatement node) {
                graphNodeStack.pop();
            }

            public void endVisit (DoStatement node) {
                GraphNode temp =  graphNodeStack.pop();
                graphNodeStack.peek().children.addAll(temp.children);
                for(GraphNode g : temp.children) {
                    g.parents.add(graphNodeStack.peek());
                }
            }

            public void endVisit (SwitchStatement node) {
                graphNodeStack.pop();
            }

            public void endVisit (SwitchCase node) {
                graphNodeStack.pop();
            }

            public void endVisit (ExpressionStatement node) {
                graphNodeStack.pop();
            }

            public void endVisit (LabeledStatement node) {
                graphNodeStack.pop();
            }

            public void endVisit (IfStatement node) {
                graphNodeStack.pop();
            }

            public void endVisit (SynchronizedStatement node) {
                graphNodeStack.pop();
            }

            public void endVisit (TryStatement node) {
                graphNodeStack.pop();
            }

            public void endVisit (CatchClause node) {
                graphNodeStack.pop();
            }

            public boolean visit (MethodDeclaration node) {
                GraphNode temp;
                temp = new GraphNode(node);
                graphNodeStack.push(temp);
                return true;
            }

            public void endVisit (MethodDeclaration node) {
                graphNodeStack.pop();
            }

            public void endVisit (VariableDeclarationStatement node) {
                graphNodeStack.pop();
            }

            public boolean visit (SingleVariableDeclaration node) {
                root.children.add(graphNodeStack.peek());
                graphNodeStack.peek().parents.add(root);
                IVariableBinding bind = node.resolveBinding();
                Set<GraphNode> set = new HashSet<>();
                set.add(graphNodeStack.peek());
                map.put(bind,set);
                return true;
            }

            public boolean visit (VariableDeclarationFragment node) {
                root.children.add(graphNodeStack.peek());
                graphNodeStack.peek().parents.add(root);
                IVariableBinding bind = node.resolveBinding();
                Set<GraphNode> set = new HashSet<>();
                set.add(graphNodeStack.peek());
                map.put(bind,set);
                node.accept(new ASTVisitor() {
                    public boolean visit (SimpleName child)
                    {
                        if(marking == 0)
                        {
                            s = (IVariableBinding) child.resolveBinding();
                            marking = 1;
                        }
                        else
                        {
                            nameSet.add((IVariableBinding) child.resolveBinding());
                        }
                        return false;
                    }
                });
                return true;
            }

            public void endVisit (VariableDeclarationFragment node) {
                for (IVariableBinding v : nameSet)
                {
                    graphNodeStack.peek().getParents().addAll(map.get(v));
                    for(GraphNode g : map.get(v))
                    {
                        g.children.add(graphNodeStack.peek());
                    }
                    map.get(s).add(graphNodeStack.peek());
                }
                nameSet.clear();
                marking = 0;
            }

            public boolean visit (VariableDeclarationStatement node) {
                //System.out.println(node.fragments());
                GraphNode temp;
                temp = new GraphNode(node);
                graphNodeStack.push(temp);
                return true;
            }

            public boolean visit (Assignment node) {
                node.accept(new ASTVisitor() {

                    public boolean visit (SimpleName child)
                    {
                        if(marking == 0)
                        {
                            s = (IVariableBinding) child.resolveBinding();
                            marking = 1;
                        }
                        else
                        {
                            nameSet.add((IVariableBinding) child.resolveBinding());
                        }
                        return false;
                    }
                });

                return false;
            }

            public boolean visit (MethodInvocation node) {
                List<Expression> list = node.arguments();
                for(Expression e : list)
                {
                    e.accept(new ASTVisitor() {
                        public boolean visit (SimpleName child) {
                            nameSet.add((IVariableBinding) child.resolveBinding());
                            return false;
                        }
                    });
                }
                return false;
            }

            public boolean visit (PrefixExpression node) {
                node.accept(new ASTVisitor() {
                    public boolean visit (SimpleName child) {
                        s = (IVariableBinding) child.resolveBinding();
                        return false;
                    }
                });
                return false;
            }

            public void endVisit (PrefixExpression node) {
                map.get(s).add(graphNodeStack.peek());
            }

            public boolean visit (PostfixExpression node) {
                node.accept(new ASTVisitor() {
                    public boolean visit (SimpleName child) {
                        s = (IVariableBinding) child.resolveBinding();
                        return false;
                    }
                });
                return false;
            }

            public void endVisit (PostfixExpression node) {
                map.get(s).add(graphNodeStack.peek());
            }

            public void endVisit (MethodInvocation node) {
                for (IVariableBinding v : nameSet)
                {
                    graphNodeStack.peek().getParents().addAll(map.get(v));
                    for(GraphNode g : map.get(v))
                    {
                        g.children.add(graphNodeStack.peek());
                    }
                }
                nameSet.clear();
            }

            public void endVisit (Assignment node) {
                for (IVariableBinding v : nameSet)
                {
                    graphNodeStack.peek().getParents().addAll(map.get(v));
                    for(GraphNode g : map.get(v))
                    {
                        g.children.add(graphNodeStack.peek());
                    }
                    map.get(s).add(graphNodeStack.peek());
                }
                nameSet.clear();
                marking = 0;
            }

            public void postVisit (ASTNode node) {

            }
        });

        return root;
    }
}

