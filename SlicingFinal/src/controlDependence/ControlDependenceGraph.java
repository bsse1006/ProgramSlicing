package controlDependence;

import graph.GraphNode;
import org.eclipse.jdt.core.dom.*;

import java.io.IOException;
import java.util.*;

public class ControlDependenceGraph {

    CompilationUnit compilationUnit;
    GraphNode root;
    Stack<GraphNode> graphNodeStack = new Stack<GraphNode> ();
    Set<GraphNode> assertNodeSet = new HashSet<>();
    Set<GraphNode> tryBodySet = new HashSet<>();
    int marker = 0;

    public ControlDependenceGraph(CompilationUnit compilationUnit) {
        this.compilationUnit = compilationUnit;
    }

    public GraphNode getControlDependenceGraph() {
        root = new GraphNode();
        graphNodeStack.push(root);

        compilationUnit.accept(new ASTVisitor() {
            public void preVisit (ASTNode node) {

            }

            public boolean visit (IfStatement node) {
                GraphNode temp;
                temp = new GraphNode(node);
                temp.parents.add(graphNodeStack.peek());
                graphNodeStack.peek().children.add(temp);
                graphNodeStack.push(temp);
                for(GraphNode g: assertNodeSet)
                {
                    g.children.add(temp);
                    temp.parents.add(g);
                }
                if(marker == 1)
                    tryBodySet.add(temp);
                return true;
            }

            public boolean visit (SynchronizedStatement node) {
                GraphNode temp;
                temp = new GraphNode(node);
                temp.parents.add(graphNodeStack.peek());
                graphNodeStack.peek().children.add(temp);
                graphNodeStack.push(temp);
                for(GraphNode g: assertNodeSet)
                {
                    g.children.add(temp);
                    temp.parents.add(g);
                }
                if(marker == 1)
                    tryBodySet.add(temp);
                return true;
            }

            public boolean visit (ForStatement node) {
                //System.out.println(node.getExpression());
                GraphNode temp;
                temp = new GraphNode(node);
                temp.parents.add(graphNodeStack.peek());
                graphNodeStack.peek().children.add(temp);
                graphNodeStack.push(temp);
                for(GraphNode g: assertNodeSet)
                {
                    g.children.add(temp);
                    temp.parents.add(g);
                }
                if(marker == 1)
                    tryBodySet.add(temp);
                return true;
            }

            public boolean visit (EnhancedForStatement node) {
                //System.out.println(node.getExpression());
                GraphNode temp;
                temp = new GraphNode(node);
                temp.parents.add(graphNodeStack.peek());
                graphNodeStack.peek().children.add(temp);
                graphNodeStack.push(temp);
                for(GraphNode g: assertNodeSet)
                {
                    g.children.add(temp);
                    temp.parents.add(g);
                }
                if(marker == 1)
                    tryBodySet.add(temp);
                return true;
            }

            public boolean visit (WhileStatement node) {
                //System.out.println(node);
                GraphNode temp;
                temp = new GraphNode(node);
                temp.parents.add(graphNodeStack.peek());
                graphNodeStack.peek().children.add(temp);
                graphNodeStack.push(temp);
                for(GraphNode g: assertNodeSet)
                {
                    g.children.add(temp);
                    temp.parents.add(g);
                }
                if(marker == 1)
                    tryBodySet.add(temp);
                return true;
            }

            public boolean visit (DoStatement node) {
                //System.out.println(node);
                GraphNode temp;
                temp = new GraphNode(node);
                temp.parents.add(graphNodeStack.peek());
                graphNodeStack.peek().children.add(temp);
                graphNodeStack.push(temp);
                for(GraphNode g: assertNodeSet)
                {
                    g.children.add(temp);
                    temp.parents.add(g);
                }
                if(marker == 1)
                    tryBodySet.add(temp);
                return true;
            }

            public boolean visit (SwitchStatement node) {
                //System.out.println(node.getExpression());
                GraphNode temp;
                temp = new GraphNode(node);
                temp.parents.add(graphNodeStack.peek());
                graphNodeStack.peek().children.add(temp);
                graphNodeStack.push(temp);
                for(GraphNode g: assertNodeSet)
                {
                    g.children.add(temp);
                    temp.parents.add(g);
                }
                if(marker == 1)
                    tryBodySet.add(temp);
                return true;
            }

            public boolean visit (SwitchCase node) {
                //System.out.println(node.getExpression());
                GraphNode temp;
                temp = new GraphNode(node);
                temp.parents.add(graphNodeStack.peek());
                graphNodeStack.peek().children.add(temp);
                graphNodeStack.push(temp);
                for(GraphNode g: assertNodeSet)
                {
                    g.children.add(temp);
                    temp.parents.add(g);
                }
                if(marker == 1)
                    tryBodySet.add(temp);
                return true;
            }

            public boolean visit (LabeledStatement node) {
                //System.out.println(node.getExpression());
                GraphNode temp;
                temp = new GraphNode(node);
                temp.parents.add(graphNodeStack.peek());
                graphNodeStack.peek().children.add(temp);
                graphNodeStack.push(temp);
                for(GraphNode g: assertNodeSet)
                {
                    g.children.add(temp);
                    temp.parents.add(g);
                }
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
                temp.parents.add(graphNodeStack.peek());
                graphNodeStack.peek().children.add(temp);
                graphNodeStack.push(temp);
                for(GraphNode g: assertNodeSet)
                {
                    g.children.add(temp);
                    temp.parents.add(g);
                }
                marker = 1;
                return true;
            }

            public boolean visit (CatchClause node) {
                //System.out.println(node.getExpression());
                marker = 0;
                GraphNode temp;
                temp = new GraphNode(node);
                graphNodeStack.push(temp);
                for(GraphNode g: assertNodeSet)
                {
                    g.children.add(temp);
                    temp.parents.add(g);
                }
                for(GraphNode g: tryBodySet)
                {
                    g.children.add(temp);
                    temp.parents.add(g);
                }
                return true;
            }

            public boolean visit (ExpressionStatement node) {
                //System.out.println(node);
                GraphNode temp;
                temp = new GraphNode(node);
                temp.parents.add(graphNodeStack.peek());
                graphNodeStack.peek().children.add(temp);
                graphNodeStack.add(temp);
                for(GraphNode g: assertNodeSet)
                {
                    g.children.add(temp);
                    temp.parents.add(g);
                }
                if(marker == 1)
                    tryBodySet.add(temp);
                return true;
            }

            public boolean visit (BreakStatement node) {
                //System.out.println(node);
                GraphNode temp;
                temp = new GraphNode(node);
                temp.parents.add(graphNodeStack.peek());
                graphNodeStack.peek().children.add(temp);
                for(GraphNode g: assertNodeSet)
                {
                    g.children.add(temp);
                    temp.parents.add(g);
                }
                if(marker == 1)
                    tryBodySet.add(temp);
                return false;
            }

            public boolean visit (ContinueStatement node) {
                //System.out.println(node);
                GraphNode temp;
                temp = new GraphNode(node);
                temp.parents.add(graphNodeStack.peek());
                graphNodeStack.peek().children.add(temp);
                for(GraphNode g: assertNodeSet)
                {
                    g.children.add(temp);
                    temp.parents.add(g);
                }
                if(marker == 1)
                    tryBodySet.add(temp);
                return false;
            }

            public boolean visit (ReturnStatement node) {
                //System.out.println(node);
                GraphNode temp;
                temp = new GraphNode(node);
                temp.parents.add(graphNodeStack.peek());
                graphNodeStack.peek().children.add(temp);
                for(GraphNode g: assertNodeSet)
                {
                    g.children.add(temp);
                    temp.parents.add(g);
                }
                if(marker == 1)
                    tryBodySet.add(temp);
                return false;
            }

            public boolean visit (ThrowStatement node) {
                //System.out.println(node);
                GraphNode temp;
                temp = new GraphNode(node);
                temp.parents.add(graphNodeStack.peek());
                graphNodeStack.peek().children.add(temp);
                for(GraphNode g: assertNodeSet)
                {
                    g.children.add(temp);
                    temp.parents.add(g);
                }
                if(marker == 1)
                    tryBodySet.add(temp);
                return false;
            }

            public boolean visit (AssertStatement node) {
                //System.out.println(node);
                GraphNode temp;
                temp = new GraphNode(node);
                temp.parents.add(graphNodeStack.peek());
                graphNodeStack.peek().children.add(temp);
                for(GraphNode g: assertNodeSet)
                {
                    g.children.add(temp);
                    temp.parents.add(g);
                }
                assertNodeSet.add(temp);
                if(marker == 1)
                    tryBodySet.add(temp);
                return false;
            }

            public boolean visit (EmptyStatement node) {
                //System.out.println(node);
                GraphNode temp;
                temp = new GraphNode(node);
                temp.parents.add(graphNodeStack.peek());
                graphNodeStack.peek().children.add(temp);
                for(GraphNode g: assertNodeSet)
                {
                    g.children.add(temp);
                    temp.parents.add(g);
                }
                return false;
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
                tryBodySet.clear();
            }

            public void endVisit (CatchClause node) {
                graphNodeStack.pop();
            }

            public boolean visit (MethodDeclaration node) {
                GraphNode temp;
                temp = new GraphNode(node);
                temp.parents.add(graphNodeStack.peek());
                graphNodeStack.peek().children.add(temp);
                graphNodeStack.push(temp);
                return true;
            }

            public void endVisit (MethodDeclaration node) {
                graphNodeStack.pop();
            }

            public void endVisit (VariableDeclarationStatement node) {
                graphNodeStack.pop();
            }

            public boolean visit (VariableDeclarationStatement node) {
                //System.out.println(node.fragments());
                GraphNode temp;
                temp = new GraphNode(node);
                temp.parents.add(graphNodeStack.peek());
                graphNodeStack.peek().children.add(temp);
                graphNodeStack.add(temp);
                for(GraphNode g: assertNodeSet)
                {
                    g.children.add(temp);
                    temp.parents.add(g);
                }
                if(marker == 1)
                    tryBodySet.add(temp);

                return true;
            }

            public void postVisit (ASTNode node) {

            }
        });

        return root;
    }
}
