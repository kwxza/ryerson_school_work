import heapq


def getName():
    return "Kipusi, Kasaine"


class MyHuffman():

    def __init__(self):
        # The instance variables will hold
        # the Huffman tree and character encoding.
        self.huffmanRootNode = None
        self.charCodes = {}

    def build(self, weights):
        # Build a huffman tree from the dictionary of character:value pairs

        # The build() function takes the given dictionary
        # of character frequency weights and iterates through it
        # to create a priority queue of the initial character
        # nodes that will be used to construct the Huffman tree.
        #
        # The queue prioritizes nodes in descending
        # importance of:
        #   1. Smallest frequency value.
        #   2. Most recently-created.
        #   3. Alphabetical order.
        #
        # For each cycle of the while loop below,
        # the 2 highest-priority nodes are released
        # from the queue. A new node is then created
        #  and added back to the queue.
        #
        # The number of nodes created is updated and
        # added to each new node at its creation.
        # This allows proper prioritization of the
        # nodes within the queue.
        #
        # The loop's iteration continues until
        # one node is left in the queue.
        # This final node will be the root
        # of the Huffman tree.
        #
        # The bitcodes of each character are then
        # found by recursively traversing the tree
        # using the setCharCodes() function.

        nodeQueue = []
        createdNodeCount = 0

        for char, freq in weights.items():
            charNode = Node(freq, char)
            heapq.heappush(nodeQueue, charNode)

        while len(nodeQueue) > 1:
            smallerNode = heapq.heappop(nodeQueue)
            largerNode = heapq.heappop(nodeQueue)
            newFreq = smallerNode.value + largerNode.value
            createdNodeCount += 1
            createdNode = Node(newFreq, createdNodeCount,
                               smallerNode, largerNode)
            heapq.heappush(nodeQueue, createdNode)

        self.huffmanRootNode = nodeQueue[0]
        self.setCharCodes(self.huffmanRootNode)
        return self

    def setCharCodes(self, node, bitcode=""):
        # Recursively traverses the tree in
        # pre-order and sets the bitcode
        # of the chars found. The bitcode string
        # is built up by being passed through
        # recursive calls.

        # If the node holds a character value,
        # add to the charCodes dictionary
        # with the character as the key and
        # the bitcode string as its value.
        if type(node.data) is str:
            self.charCodes[node.data] = bitcode

        # If the node does not hold a character
        # value, then recurse first to the left
        # node and then the right node until
        # a node with a character value is reached.
        # Each recursion to the left adds "1" to
        # the bitcode string; recursing to the
        # right adds "0" to the bitcode string.
        else:
            if node.left is not None:
                self.setCharCodes(node.left, bitcode + "1")
            if node.right is not None:
                self.setCharCodes(node.right, bitcode + "0")
        return self

    """
        The encode() and decode() methods were originally
        written to be tail-recursive, with a running
        accumulator building up the encoded bitstring
        or decoded word respectively.

        This worked well for cases where the input
        word/bitstring being recursed over contained
        less than ~1000 characters.

        However, Python does not do tail-call optimization
        and has a default recursion limit of 1000.
        Thus, the methods would fail for inputs larger
        than ~1000 characters.

        The methods were re-written iteratively to
        avoid this problem.
    """

    def encode(self, word):
        # Return the bitstring of word encoded by the rules of your huffman tree

        # Encoding occurs iteratively.
        # Each character of the word is searched
        # in the charCodes dictionary and the
        # result is concatenated to the bitstring.
        #
        # If the character is not within the charCodes
        # dicitionary, searching for the character
        # will return None.
        #
        # Attempting to concatenate a string with
        # None will raise a TypeError.
        # For this reason, the concatenation is
        # wrapped in a try/except block.
        #
        # Once the iteration is complete,
        # the resulting bitstring is returned.

        bitstring = ""

        for char in range(len(word)):
            try:
                bitstring += self.charCodes.get(word[char])
            except TypeError:
                return print(
                    f"The character {word[char]} is not encoded in this Huffman tree!")

        return bitstring

    def decode(self, bitstring):
        # Return the word encoded in bitstring, or None if the code is invalid

        # Decoding the bitstring works by traversing
        # the Huffman tree one node at a time,
        # starting from the root.
        #
        # For each node reached, the data type
        # it contains is checked.
        #
        # If the data type is str, that character
        # is added to the word string and node
        # traversal restarts from the root.
        #
        # If the data type is int, the tree
        # traversal moves one node downwards.
        # If the first bit of the bitstring is "1",
        # traversal moves down to the left.
        # Otherwise, traversal goes to the right.
        #
        # Each time the traversal goes deeper
        # into the tree, the first bit is removed
        # from the bitstring.
        # Traversal continues until there are no
        # more bits left in the bitstring.
        #
        # The success of the decoding can be
        # determined by checking the data type
        # contained by the final node reached
        # by traversal.
        #
        # If the final node contains a str,
        # decoding is successful. The character
        # in the final node is concatenated to
        # the word string and the result is returned.
        #
        # Otherwise, the provided bitstring is
        # invalid. In this case, the decode()
        # function will return None.

        word = ""
        node = self.huffmanRootNode

        while len(bitstring) > 0:

            if type(node.data) is str:
                word += node.data
                node = self.huffmanRootNode

            else:
                node = node.left if bitstring[0] == "1" \
                    else node.right
                bitstring = bitstring[1:]

        if type(node.data) is int:
            return None

        return word + node.data


class Node:
    # The Node class is designed to allow the build()
    # method of the MyHuffman class to use Python's
    # heapq module to create a priority queue, which
    # will be used to create the Huffman tree.

    def __init__(self, value, data, left=None, right=None):
        # @value - This is the frequency of the node.
        # @data - This will be either a str or int value.
        #         >> str:
        #           - The character that the node frequency
        #             corresponds to.
        #             (ie - only for alphabet nodes).
        #         >> int:
        #           - The creation order of the node.
        #             (ie - only for created nodes).
        # @left/right - Pointers to any sub-nodes.
        self.value = value
        self.data = data
        self.left = left
        self.right = right

    # Python's default comparison operators are
    # overridden with methods that will accurately
    # compare the nodes forming the Huffman tree.
    #
    # Nodes are compared by the natural order of
    # their @value attributes (ie - their frequencies).
    #
    # If the @value attributes are equal, then
    # the @data attributes are compared.
    #
    # If the @data attributes are of the same type,
    # their natural order can be compared using
    # the less-than/greater-than operators (</>).
    #
    # If the @data attributes are of different types,
    # then the node with @data type of int (ie - the
    # created node) is treated as smaller.
    #
    # If both the @value and @data attributes are
    # equal, then the nodes are equal for comparative
    # purposes.

    # Less-than operation
    def __lt__(self, other):
        if self.value == other.value:
            if type(self.data) == type(other.data):
                return self.data < other.data
            else:
                return True if type(self.data) is int \
                    else False
        return self.value < other.value

    # Less-than-or-equal operation
    def __le__(self, other):
        if (self.value, self.data) == (other.value, other.data):
            return True
        return self < other

    # Greater-than operation
    def __gt__(self, other):
        if self.value == other.value:
            if type(self.data) == type(other.data):
                return self.data > other.data
            else:
                return True if type(self.data) is str \
                    else False
        return self.value > other.value

    # Greater-than-or-equal operation
    def __ge__(self, other):
        if (self.value, self.data) == (other.value, other.data):
            return True
        return self > other
