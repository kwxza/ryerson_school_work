def getName():
    return "Kipusi, Kasaine"


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

    def getHeight(self):
        # Return the height of this node
        return self.height

    def insert(self, data):
        # Insert data into the tree, descending from this node
        # Ensure the tree will be complete - every level is filled save for the last, and each node is as far left as possible
        # Return this node after data has been inserted

        # Check if the right node is empty.
        if self.right is None:
            # Check if the left node is also empty.
            if self.left is None:
                # If the both nodes are empty,
                # insert new node to the left.
                self.left = MyTree(data)
            # If only the right node is empty,
            # insert new node to the right.
            else:
                self.right = MyTree(data)

            # Increment the number of descendents of the node.
            self.descendents += 1

        # If both nodes are filled, determine which
        # direction that the insertion path will go.
        else:
            # Logic:
            #
            # The maximum number of children that a
            # node can have is calculated from the
            # node's height within the tree, as long
            # as the height is greater than 0.
            #
            #   -> Max Children = 2^(height + 1) - 2
            #
            # Insertion will recurse towards the left
            # as long as the left node has less children
            # than the maximum for its height.
            #
            # If the left node is completely filled,
            # insertion recurses towards the right node.
            #
            # If the number of children are equivalent,
            # this means one of two things:
            #
            #   a) Both left and right nodes have their
            #      maximum number of children, so recursion
            #      must descend to a lower level of the tree.
            #
            #   b) The lowest level of the tree has been
            #      reached (node height == 0).
            #      All nodes at this height have no
            #      children, so a new level will be created
            #      and the data will be inserted into the
            #      tree during the next recursive call.
            #
            # For both of these cases, insertion will
            # recurse towards the leftmost node.

            if self.left.descendents < pow(2, self.left.height + 1) - 2 \
                    or self.left.descendents == self.right.descendents:
                self.left = self.left.insert(data)
            else:
                self.right = self.right.insert(data)

            # After insertion has occurred, the node's
            # child count is updated.
            #
            # This equates to the sum of the children of
            # the left and right nodes, plus 2 to account
            # for the left and right nodes themselves.
            #
            # As recursion unwinds, each node in the path
            # travelled through the tree will have its
            # child count updated.
            self.descendents = self.left.descendents + self.right.descendents + 2

        # Finally, the height of the node can be updated.
        # The longest branch of the tree will always be
        # the leftmost path, so the node's height will
        # be the height of its left node + 1.
        self.height = self.left.height + 1

        # Return the original node.
        return self


class MyBST(MyTree):
    def __init__(self, data):
        # Initialize this node, and store data in it
        super().__init__(data)

    def calculateHeight(self):
        # Determining the height of a node:
        #   - If the node has no children,
        #     its height is zero.
        #   - If the node has children, its
        #     height is found by adding 1 to
        #     the height of its tallest child.
        if self.descendents == 0:
            return 0
        elif self.left is not None and self.right is not None:
            return max(self.left.height, self.right.height) + 1
        elif self.left is not None:
            return self.left.height + 1
        elif self.right is not None:
            return self.right.height + 1

    def insert(self, data):
        # Insert data into the tree, descending from this node
        # Ensure that the tree will be a valid Binary Search Tree
        # Return this node after data has been inserted

        # Duplicate keys are acceptable and treated
        # as larger than their duplicate.

        # If the given data is less than the node:
        #   - If no left node exists, create and insert data.
        #   - Otherwise, recurse towards the left.
        if data < self.data:
            if self.left is None:
                self.left = MyBST(data)
            else:
                self.left = self.left.insert(data)

        # If the given data is greater than or equal to the node:
        #   - If no right node exists, create and insert data.
        #   - Otherwise, recurse towards the right.
        else:
            if self.right is None:
                self.right = MyBST(data)
            else:
                self.right = self.right.insert(data)

        # Increment the number of children of the node
        # once the new data has been inserted.
        self.descendents += 1

        # Determine the height of the node using
        # the calculateHeight() method defined above.
        self.height = self.calculateHeight()

        # Return the original node.
        return self

    def __contains__(self, data):
        # Returns true if data is in this node or a node descending from it
        if data == self.data:
            return True
        elif data < self.data and self.left is not None:
            return self.left.__contains__(data)
        elif data > self.data and self.right is not None:
            return self.right.__contains__(data)
        # Return false if none of the conditions are fulfilled.
        return False


class MyAVL(MyBST):
    def __init__(self, data):
        # Initialize this node, and store data in it
        super().__init__(data)
        # Defining a new instance variable to hold
        # the balance factor of a node.
        self.balanceFactor = 0

    def getBalanceFactor(self):
        # Return the balance factor of this node

        # If the node's left and/or right child does not exist,
        # then the height of the non-existing child is zero.
        #
        # If the left and/or right child exists, we add 1 to its
        # height to be able to calculate the balance factor.
        left = 0 if self.left is None else self.left.height + 1
        right = 0 if self.right is None else self.right.height + 1
        return left - right

    def calculateChildren(self):
        # Determining the number of children of the node:
        #   - The sum of the children of its left and right
        #     nodes, if those nodes exist.
        #   - Otherwise, zero.
        descendents = 0
        if self.left is not None:
            descendents += self.left.descendents + 1
        if self.right is not None:
            descendents += self.right.descendents + 1
        return descendents

    def updateNodeData(self):
        # This function updates the child count,
        # height and balance factor of a node
        # and then returns that node.
        #
        # This makes use of the calculateChildren()
        # and getBalanceFactor() methods defined above,
        # as well as the calculateHeight() method that
        # is inherited from the MyBST class.
        self.descendents = self.calculateChildren()
        self.height = self.calculateHeight()
        self.balanceFactor = self.getBalanceFactor()
        return self

    def insert(self, data):
        # Insert data into the tree, descending from this node
        # Ensure that the tree will be a valid AVL tree
        # Return the node in this node's position after data has been inserted

        # Duplicate insertions are treated
        # as larger than their duplicate,
        # and inserted towards the right.

        if data < self.data:
            # Insert/recurse towards the left.
            self.left = MyAVL(data) if self.left is None \
                else self.left.insert(data)
        else:
            # Insert/recurse towards the right.
            self.right = MyAVL(data) if self.right is None \
                else self.right.insert(data)

        # After insertion, update the child count,
        # height and balance factor of the node.
        self = self.updateNodeData()

        # If the node is unbalanced, update the
        # node to its rebalanced form.
        if not -1 <= self.balanceFactor <= 1:
            self = self.rebalance()

        # Return the node in this node's position.
        return self

    def rebalance(self):
        # This function will rebalance the node and
        # return the newly balanced node.

        # --> Left-heavy nodes will have a
        #     positive balance factor.
        #
        # --> Right-heavy nodes will have a
        #     negative balance factor.
        #
        # To determine if an unbalanced node requires
        # a single rotation vs a double rotation:
        #
        #   --> Check the left/right child node
        #       if the parent node is left/right-heavy.
        #       (ie - same direction as heaviness).
        #
        #   --> If the sign of the child node's balance
        #       factor is the same as the parent's,
        #       then a double rotation is necessary.
        #
        #   --> Otherwise, only a single rotation
        #       is necessary.

        # If the balance factor is greater than 1,
        # the node is left-heavy and requires a
        # right rotation.
        if self.balanceFactor > 1:
            # If the left child of the node is right-heavy,
            # then the left child must first be rotated left.
            if self.left.balanceFactor == -1:
                self.left = self.left.leftRotate()
            # After checking/rotating the child node,
            # perform the right rotation.
            self = self.rightRotate()

        # If the balance factor is less than -1,
        # the node is right-heavy and requires
        # a left rotation.
        if self.balanceFactor < -1:
            # If the right child of the node is left-heavy,
            # then the right child must first be rotated right.
            if self.right.balanceFactor == 1:
                self.right = self.right.rightRotate()
            # After checking/rotating the child node,
            # perform the left rotation.
            self = self.leftRotate()

        # Return the rebalanced node.
        return self

    def leftRotate(self):
        # Perform a left rotation on this node and return the new node in its spot

        # In a left rotation, the node's right child
        # will become the new root and the original
        # node will become the new root's left child.
        newRoot = self.right
        oldRoot = self

        # The old root's right child will be changed
        # to match the new root's left child.
        oldRoot.right = None if newRoot.left is None \
            else newRoot.left

        # The child count, height and balance factor
        # of the old root must be updated.
        oldRoot = oldRoot.updateNodeData()

        # Now the old root can be put into place
        # as the left child of the new root.
        # Then the child count, height and balance
        # factor of the new root must be updated.
        newRoot.left = oldRoot
        newRoot = newRoot.updateNodeData()

        # Return the new root.
        return newRoot

    def rightRotate(self):
        # Perform a right rotation on this node and return the new node in its spot

        # In a right rotation, the node's left child
        # will become the new root and the original
        # node will become the new root's right child.
        newRoot = self.left
        oldRoot = self

        # The old root's left child will be changed
        # to match the new root's right child.
        oldRoot.left = None if newRoot.right is None \
            else newRoot.right

        # The child count, height and balance factor
        # of the old root must be updated.
        oldRoot = oldRoot.updateNodeData()

        # Now the old root can be put into place
        # as the right child of the new root.
        # Then the child count, height and balance
        # factor of the new root must be updated.
        newRoot.right = oldRoot
        newRoot = newRoot.updateNodeData()

        # Return the new root.
        return newRoot
