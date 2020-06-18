class MyTrie:
    def __init__(self):
        # Initialize the trie node as needed
        pass
    
    def insert(self, word):
        # Insert a word into this trie node
        pass

    def exists(self, word, position=0):
        # Return true if the passed word exists in this trie node
        # A terminal node will return true if the word passed is ""
        pass

    def isTerminal(self):
        # Return true if this node is the terminal point of a word
        pass

    def autoComplete(self, prefix, position=0):
        # Return every word that extends this prefix in alphabetical order
        pass

    def __len__(self):
        # Return the number of words that either terminate at this node or descend from this node
        # A terminal leaf should have length 1, the node A with terminal child leaves B|C should have length 2
        pass
