def getName():
    return "Kipusi, Kasaine"


class MyTrie:
    def __init__(self, *, char=None):
        # Initialize the trie node as needed

        # @char is used to differentiate
        # root/terminal nodes from nodes
        # representing characters.
        # This distinction is important for
        # the logic of the insert() method.
        #
        # The default value of @char is None,
        # which initializes the value of the
        # root node when a trie is first created.
        # All other nodes will have the @char value
        # set explicitly by the insert() method.
        #
        # @charDictionary is a dictionary containing
        #  all the characters that can come next in
        # sequence after the character of the
        # node itself.
        # The keys of the dictionary are the subsequent
        # characters. The value of each key is the trie
        # node corresponding to that character.
        #
        # @length is an integer representing how many
        # terminal nodes descend from this node.
        #
        # The length value is initialized to 1 if the node
        # is a terminal node, meaning that __init__() was
        # given @char == "".
        # Otherwise, the length value is initialized to 0.
        #
        # This means that the root node of an empty trie
        # (a trie that has been newly created before any
        # words have been inserted) has a length of zero.
        #
        # This implementation seems to be consistent with
        # the definition of length being how many words
        # terminate at or descend from a node.
        # At creation, no words descend from or terminate at
        # a root node, so its length is zero.

        self.char = "" if char is None else char
        self.charDictionary = {}
        self.length = 1 if char == "" else 0

    def insert(self, word):
        # Insert a word into this trie

        # Logic of insert():
        # *****************
        # The insert method recursively travels to
        # each node of the trie that represents each
        # sequential character of the word provided.
        #
        # Characters not yet represented by nodes in
        # the recursion's path are inserted into the
        # trie as new nodes.
        #
        # This means that words already contained
        # in the trie will not be re-inserted.
        #
        # The insert() method recurses at a node
        # based on two conditions:
        #   a. The passed word is not empty.
        #   b. The node's char value is not empty.
        #
        # These conditions are not mutually exclusive:
        #   --> insert() will recurse whether both or
        #       either of these conditions are true.
        #
        # This results in 3 different cases where insert() will
        # recurse and one case where insert() will not recurse.
        #
        # No Recursion Occurs (base case):
        #   Conditions:
        #   --> Node's char is empty
        #   --> Passed word is empty
        #
        #       This case occurs in two scenarios:
        #           1. insert() at a terminal node:
        #               (ie - sequence is complete, no recursion occurs)
        #           2. insert() at the root node:
        #               (ie - invalid sequence, no recursion occurs)
        #
        #       There are two types of nodes that can have
        #       a empty char value - a terminal node or
        #       a root node.
        #
        #       This is best understood by thinking of a
        #       word as a sequence of characters, with an
        #       empty character (nothing) at the beginning
        #       and end of the sequence.
        #
        #       Each path of nodes in a trie represents a word's
        #       sequence of characters, with each node's char
        #       value being a character in the sequence.
        #
        #       For any particular word represented within a trie:
        #           - The char value of the word's root node represents
        #             what comes before the first character of the
        #             word's character sequence (ie - nothing).
        #
        #           - The char value of the word's terminal node
        #             represents what comes after the last character
        #             of the word's character sequence (ie - nothing).
        #
        #       When insert() recurses towards any particular
        #       node, the word passed to the node represents
        #       the character(s) that come next in the sequence
        #       after the current node's char value.
        #
        #       If the current character in the sequence (the node's
        #       char value) represents nothing and the next character
        #       in the sequence (the passed word) also represents nothing,
        #       then the sequence contains no new information.
        #       Thus, no recursion occurs if both the passed word and
        #       the node's char value are empty.
        #
        #       For a terminal node, this point indicates that insertion
        #       is complete, as the node's empty char value signifies
        #       the end of a word's character sequence.
        #       If the terminal node existed before the insert()
        #       method reached it, then no actual insertion occured
        #       because the provided word already existed in the trie.
        #
        #       For the root node, this point indicates that invalid
        #       input has been given to the insert() method.
        #
        # Recursion Occurs (case 1):
        #   Conditions:
        #   --> Node's char is empty
        #   --> Passed word is not empty
        #
        #       This case occurs at the root node of the trie
        #       structure (ie - the beginning of the character
        #       sequence of a word).
        #       Insertion begins from this node, so the node
        #       representing the first character of the word's
        #       character sequence will be found in/added to
        #       the root node's charDictionary.
        #       Insertion will then recurse towards this node.
        #
        # Recursion Occurs (case 2):
        #   Conditions:
        #   --> Node's char is not empty
        #   --> Passed word is empty
        #
        #       This case occurs when the current node represents
        #       the last character in a word's character sequence.
        #       At this point, insertion is complete.
        #       A terminal node (char value == "") will be added to
        #       the node's charDictionary to signify the end of the sequence.
        #       Insertion will then recurse towards this terminal node,
        #       bringing about the no-insertion case where no further
        #       recursion will occur.
        #
        # Recursion Occurs (case 3):
        #   Conditions:
        #   --> Node's char is not empty
        #   --> Passed word is not empty
        #
        #       This case occurs when the current node represents a
        #       character within the word's character sequence.
        #       At this point, insertion is not yet complete.
        #       The first character of the word's remaining character
        #       sequence will be found in/ added to the current node's
        #       charDictionary.
        #       Insertion will then recurse towards this node.
        #
        #
        # Handling the Current Node's Length:
        # **********************************
        # A node's length is equal to the number of
        # terminal nodes that descend from it.
        #
        # A terminal node is created whenever a
        # new word (character sequence) is inserted
        # into the trie.
        # Thus, as soon as a new character node is
        # created, it is known that every node
        # on its sequence path must increment its
        # length value by one.
        #
        # However, at any depth of recursion before
        # the creation of a new character node,
        # it is impossible to know if new nodes must
        # be created deeper within the recursion.
        #
        # Essentially, it is impossible to know if
        # the sequence already exists until recursion
        # reaches a character that is not already
        # within the trie.
        #
        # This means that the current node's length
        # cannot be handled by simply incrementing
        # when insert() recurses, because then calling
        # insert() with a pre-existing word would
        # incorrectly change the length of the nodes
        # along that word's character sequence path.
        #
        # Similarly, the length cannot be handled by
        # incrementing whenever a new node is created
        # because calls to insert() at nodes further up
        # the character sequence path may not have
        # created new nodes. This means that incrementing
        # at node creation would not propagate up the
        # recursive call stack to any pre-existing character
        # nodes of the inserted word.
        #
        # It is possible to accurately update the
        # current node's length after the recursive
        # call to insert() is complete by iterating
        # through all the items in the charDictionary
        # and summing their length values.
        #
        # However, this introduces unnecessary algorithmic
        # overhead as only one item's length value has
        # changed and that item is known - it is the node
        # that is recursed towards.
        #
        # Instead, if the next node in the word's character
        # sequence already exists, its length value is
        # subtracted from the current node's length value.
        # If this pre-existing node is a terminal node (length of 1),
        # then 1 is subtracted from the current node's length.
        #
        # Next, a call to insert() is made on the next node
        # in sequence, which may have been pre-existing or
        # newly-created. If the next node is a terminal node,
        # then no more recursion happens after this call.
        #
        # Once the recursive call to insert() completes,
        # the length of the next node in sequence is added
        # to the current node's length.
        # If the next node was a terminal node (length 1),
        # then 1 is added to the current node's length.
        # If the terminal node already existed, this replaces
        # the 1 that was previously subtracted from the current
        # node's length. Thus, the current node's length stays
        # the same.
        # If the terminal node is new, this 1 increments the
        # current node's length value. Thus, the current node's
        # length increases to reflect the new insertion.
        #
        # This process propagates back upwards along the
        # character sequence path as the recursion unwinds,
        # ensuring that every node along the path has the
        # correct length value.

        # Checking insertion conditions
        if word != "" or self.char != "":

            # Add the first char of word to the node's
            # charDictionary if it is not already present.
            # If it is present, subtract its length
            # from the length of the current node.
            if word[:1] not in self.charDictionary:
                self.charDictionary[word[:1]] = MyTrie(char=word[:1])
            else:
                self.length -= self.charDictionary[word[:1]].length

            # insert() must recurse towards the node
            # representing the next character in the
            # word sequence.
            self.charDictionary.get(word[:1]).insert(word[1:])
            # Add the length of the node recursed towards to
            # the current node's length.
            self.length += self.charDictionary[word[:1]].length

    def exists(self, word):
        # Return true if the passed word exists in this trie node
        # A terminal node will return true if the word passed is ""

        # A word exists in the trie if its character
        # sequence matches a path in the trie that
        # leads to a terminal node.
        #
        # To determine existence, exists() recurses
        # toward each character node in the passed
        # word's character sequence. If a character
        # is not found, exists() returns False.
        # Otherwise, exists() returns true upon
        # reaching a terminal node.

        # If the node is not a terminal node:
        if not self.isTerminal():

            # If the word's next character is in the
            # node's charDictionary:
            if word[:1] in self.charDictionary:
                # Recurse towards the next character.
                return self.charDictionary[word[:1]].exists(word[1:])

            # If the word's next character is not in
            # the node's charDictionary:
            else:
                # Return False as the word doesn't exist in the trie.
                return False

        # If the node is a terminal node:
        else:
            # Return True as a terminal node can only be
            # reached by following the recursion path of
            # a word in the trie.
            return True

    def isTerminal(self):
        # Return true if this node is the terminal point of a word

        # A node is terminal if it has an empty char value,
        # an empty charDictionary and a length of 1.
        # This definition of terminal excludes the root node
        # when the trie is empty (root would have length 0).
        return self.char == "" and len(self) == 1 and len(self.charDictionary) == 0

    def autoComplete(self, prefix, position=0):
        # Return every word that extends this prefix in alphabetical order

        # The autoComplete() method begins with an
        # empty list for all the words matching the
        # prefix. The method recurses through
        # the trie structure, following the path
        # provided by the characters of the prefix.
        # The position value is used to track the
        # character index of the prefix string
        # through successive function calls.
        #
        # This is done so that the entire prefix
        # is retained through each function call.
        # This is different from the insert() and
        # exists() methods, which remove a character
        # from the word passed forward to each
        # recursive call.
        #
        # If a character within the prefix is not
        # found along the character path of the
        # trie, autoComplete() will print an
        # error message to stdout. The method
        # then returns the list of matching words,
        # which at this point is still empty.
        #
        # Once recursion reaches the node matching
        # the last character of the prefix, the keys
        # of the node's charDictionary are sorted
        # alphabetically into a list of characters.
        # This list is then iterated in order,
        # character-by-character.
        #
        # The process for each character key in the
        # iteration is as follows:
        #
        #   - If the character is "":
        #     **********************
        #       An empty character indicates a terminal node,
        #       meaning that the current prefix is a word.
        #       In this case, the prefix is appended directly
        #       to the list of matching words. Iteration will
        #       then proceed to the next character of the
        #       current node's character key list.
        #
        #   - If the character is not "":
        #     **************************
        #       The corresponding character node is searched
        #       within the current node's charDictionary.
        #       The autoComplete() method is then called
        #       upon the node that is found. This call to
        #       autoComplete returns a list, which gets
        #       concatenated to the matchingWords list.
        #
        #       The parameters for the new function call
        #       are as follows:
        #           --> @prefix is the current prefix with the
        #               character of the current iteratation
        #               concatenated to its end.
        #
        #           --> @position is the current position incremented
        #               by one because of the change in the length of
        #               the prefix from the concatenation.
        #
        # Sorting the keys of the current node's charDictionary
        # alphabetically and iteratively recursing to each
        # possible next character ensures that the autoComplete()
        # method eventually returns a list of all matching words,
        # sorted into alphabetical order.

        matchingWords = []

        if position < len(prefix):
            try:
                return self.charDictionary[prefix[position]
                                           ].autoComplete(prefix, position + 1)
            except KeyError:
                print("ERROR: The given prefix is not found in this trie!")

        else:
            orderedCharKeys = sorted(self.charDictionary)

            for char in orderedCharKeys:
                if char == "":
                    matchingWords.append(prefix)
                else:
                    matchingWords += self.charDictionary[char].autoComplete(
                        prefix + char, position + 1)

        return matchingWords

    def __len__(self):
        # Return the number of words that either terminate at this node or descend from this node
        # A terminal leaf should have length 1, the node A with terminal child leaves B|C should have length 2
        return self.length
