def append_node_to_dict(node1, node2, dict_connections):
    if not node1 in dict_connections:
        dict_connections[node1] = [node2]
    else:
        node_connections = dict_connections[node1]
        if not node2 in node_connections:
            node_connections.append(node2)
            dict_connections[node1] = node_connections
    return dict_connections

def parse_connections(connection, dict_connections):
    splitted = connection.split('-')
    node1 = splitted[0]
    node2 = splitted[1]
    dict_connections = append_node_to_dict(node1, node2, dict_connections)
    dict_connections = append_node_to_dict(node2, node1, dict_connections)
    return dict_connections

def find_paths(start, length, dict_connections, paths):
    for path in paths:
        last_node = path[-1]

def can_visit(node, path):
    check = False
    if node.isupper():
        check = True
    elif not node in path:
        check = True
    elif node.islower() and node != 'end' and node != 'start':
        lowercase_nodes = [node for node in path if node.islower()]
        set_lowercase_nodes = set(lowercase_nodes)
        if (len(lowercase_nodes) == len(set_lowercase_nodes)):
            check = True
    return check

def find_paths(graph, start, end, path=[]):
    path = path + [start]
    if start == end:
        return [path]
    paths = []
    for node in graph[start]:
        if can_visit(node, path):
            newpaths = find_paths(graph, node, end, path)
            for newpath in newpaths:
                paths.append(newpath)
    return paths
    
    
        


    

if __name__ == '__main__':
    input = open('./day 12/Martijn - Python/input.txt').readlines()
    connections = [line.strip() for line in input]
    dict_connections = {}
    for x in range(0, len(connections)):
        dict_connections = parse_connections(connections[x], dict_connections)
    paths = find_paths(dict_connections, 'start', 'end')
    print(len(paths))