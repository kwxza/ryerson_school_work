import random
import string
import re
# Module Imports
import sys
from importlib import import_module


def generate(size):
    arr = [None] * size

    arr[size-1] = 0

    for i in range(0, size-2):
        arr[i] = random.randint(1, size)

    return arr


def pathTest(vector, path):
    def pathTest_(vector, path, position, location):
        if(location == len(path)-1):
            if(position == len(vector) - 1):
                return True
            else:
                return False
        elif location >= len(vector) or location < 0:
            return False
        else:
            return pathTest_(vector, path, position + vector[path[location]], location + 1) or pathTest_(vector, path, position - vector[path[location]], location + 1)

    return pathTest_(vector, path, 0, 0)


def Test(lib, seed=0, size=10, verbose=False):
    known = [
        ([2, 8, 3, 2, 7, 2, 2, 3, 2, 1, 3, 0], 4),  # Many solutions, handle it
        ([2, 3, 1, 1, 0], 2),  # Simple infinite loops
        ([3, 1, 1, 1, 3, 4, 2, 5, 3, 0], 0),  # Zero solutions
        ([4, 3, 1, 2, 3, 5, 4, 2, 2, 1, 1, 0], 5),  # Complicated loops
        ([4, 4, 1, 2, 3, 1, 8, 2, 0], 0)  # Infinite loops, zero solutions
    ]
    for (vector, solutions) in known:

        finder = lib.Pathfinder(vector)
        count = 0
        if verbose:
            print(f"Vector: {vector}")
        for path in finder.getPaths():
            if pathTest(vector, path):
                count += 1
                if verbose:
                    print("Valid solution: ", end="")
                    print(path)
            else:
                if verbose:
                    print("Error: Invalid path returned")
                return False
        if count != solutions:
            if verbose:
                print("Error: Incorrect number of solutions")
            return False
        if verbose:
            print()

        yield True


if __name__ == "__main__":
    if len(sys.argv) < 2:
        print("Include at least a library name as an argument.")
        exit()
    name = sys.argv[1]
    if name.endswith(".py"):
        name = name[:-3]
    print(f"Testing module {name}")
    module = import_module(name, package=__name__)
    score = 0
    for i in Test(module, seed=123456, size=20, verbose=True):
        if i:
            score += 1
        else:
            break

    print(f"Test result: {score}/5")
