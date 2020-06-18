import heapq


def getName():
    return "Last name, first name"


class MyHuffman():
    def build(self, weights):
        # Build a huffman tree from the dictionary of character:value pairs
        pass

    def encode(self, word):
        # Return the bitstring of word encoded by the rules of your huffman tree
        pass

    def decode(self, bitstring):
        # Return the word encoded in bitstring, or None if the code is invalid
        pass

# This node structure might be useful to you


class Node:
    def __init__(self, value, data, left=None, right=None):
        pass

    def __lt__(self, other):
        pass

    def __le__(self, other):
        pass

    def __gt__(self, other):
        pass

    def __ge__(self, other):
        pass
