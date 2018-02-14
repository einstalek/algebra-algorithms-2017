import numpy as np
from numpy.linalg import eigh


def sparsest_cut():
    name_to_index_map = dict()
    edges_map = dict()

    edges_count = int(input())
    for i in range(edges_count):
        v1, v2 = list(map(int, input().split(" ")))
        if v1 not in edges_map.keys():
            name_to_index_map[v1] = len(edges_map)
            edges_map[v1] = []
        if v2 not in edges_map.keys():
            name_to_index_map[v2] = len(edges_map)
            edges_map[v2] = []
        edges_map[v1].append(v2)
        edges_map[v2].append(v1)

    index_to_name_map = {v: k for k, v in name_to_index_map.items()}
    vertices_count = len(name_to_index_map.keys())
    matrix = np.zeros(shape=(vertices_count, vertices_count), dtype=np.float64)
    for i in range(vertices_count):
        for j in range(vertices_count):
            v1 = index_to_name_map[i]
            v2 = index_to_name_map[j]
            if i == j:
                matrix[i, j] = len(edges_map[v1])
            elif v2 in edges_map[v1]:
                matrix[i, j] = -1
    w, v = eigh(matrix)
    second_smallest_sorted = sorted(v[:, 1], reverse=True)

    permutations = [list(v[:, 1]).index(x) for x in second_smallest_sorted]
    vertices = [index_to_name_map[x] for x in permutations]
    density = []
    powers = []
    for k in range(1, vertices_count):
        a = permutations[:k]
        v_wth_a = permutations[k:]
        e_count = 0
        for v1 in a:
            i = index_to_name_map[v1]
            for v2 in v_wth_a:
                j = index_to_name_map[v2]
                if j in edges_map[i]:
                    e_count += 1
        density.append(vertices_count * e_count / (k * (vertices_count - k)))
        powers.append(len(a))

    if np.argmin(density) <= vertices_count // 2:
        for x in vertices[:1 + np.argmin(density)]:
            print(x, " ", end="")
    else:
        for x in vertices[1 + np.argmin(density):]:
            print(x, " ", end="")

if __name__ == "__main__":
    sparsest_cut()