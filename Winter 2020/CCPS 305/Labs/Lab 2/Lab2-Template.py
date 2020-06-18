def getName():
    return "Last name, first name"


class MyTree():
    def __init__(self, data):
        # Initialize this node, and store data in it
        self.data = data
        self.left = None
        self.right = None
        self.height = 0
        self.descendents = 0

    def getLeft(self):
        # Return the left child of this node, or None
        return self.left

    def getRight(self):
        # Return the right child of this node, or None
        return self.right

    def getData(self):
        # Return the data contained in this node
        return self.data

    def insert(self, data):
        # Insert data into the tree, descending from this node
        # Ensure the tree remains complete - every level is filled save for the last, and each node is as far left as possible
        # Return this node after data has been inserted
        pass

    def getHeight(self):
        # Return the height of this node
        pass


class MyBST(MyTree):
    def __init__(self, data):
        # Initialize this node, and store data in it
        super().__init__(data)
        pass

    def insert(self, data):
        # Insert data into the tree, descending from this node
        # Ensure that the tree remains a valid Binary Search Tree
        # Return this node after data has been inserted
        pass

    def __contains__(self, data):
        # Returns true if data is in this node or a node descending from it
        pass


class MyAVL(MyBST):
    def __init__(self, data):
        # Initialize this node, and store data in it
        super().__init__(data)
        pass

    def getBalanceFactor(self):
        # Return the balance factor of this node
        pass

    def insert(self, data):
        # Insert data into the tree, descending from this node
        # Ensure that the tree remains a valid AVL tree
        # Return the node in this node's position after data has been inserted
        pass

    def leftRotate(self):
        # Perform a left rotation on this node and return the new node in its spot
        pass

    def rightRotate(self):
        # Perform a right rotation on this node and return the new node in its spot
        pass
