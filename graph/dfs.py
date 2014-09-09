class Graph:
    def __init__(self):
        self.edges = {}

    def addVertex(self,v):
        """All vertices are unique!"""
        if v not in self.edges:
            self.edges[v] = []

    def addEdge(self,from_v,to_v):
        """Add edge to graph"""
        if from_v not in self.edges:
            self.edges[from_v] = []
        if to_v not in self.edges:
            self.edges[to_v] = []
        if to_v not in self.edges[from_v]:
            self.edges[from_v].append(to_v)
        if from_v not in self.edges[to_v]:
            self.edges[to_v].append(from_v)
    def isEdge(self,from_v,to_v):
        if to_v not in self.edges:
            return False
        if from_v not in self.edges:
            return False
        return to_v in self.edges[from_v]

def loadGraph(edges):
    g = Graph()
    for v in edges:
        g.addVertex(v)
        for neighbor in edges[v]:
            g.addEdge(v,neighbor)
    return g

White = 0
Gray = 1
Black = 2

class DepthFirstTraversal:
    def __init__(self,graph,s):
        """Initiates DFS starting at s"""
        self.graph = graph
        self.start = s
        self.color = {}
        self.pred = {}

        # Initial state
        for v in graph.edges:
            self.color[v] = White
            self.pred[v] = None

        self.dfs_visit(s)

    def dfs_visit(self,u):
        """Recursive traversal of graph using DFS"""
        self.color[u] = Gray

        for v in self.graph.edges[u]: # for all adjacent vertices
            if self.color[v] is White: # that have White color
                self.pred[v] = u       # memorize predecessor
                self.dfs_visit(v)      # recurse on this adjacent vertex

        self.color[u] = Black

    def solution(self,v):
        """Recover path from start to this vertex v"""
        if v not in self.graph.edges:
            return None
        path = [v]

        while v != self.start:
            v = self.pred[v]
            path.insert(0,v)

        return path
        
