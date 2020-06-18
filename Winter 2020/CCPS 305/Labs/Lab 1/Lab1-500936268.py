def getName():
    return "Kipusi, Kasaine"


class MyQueue:
    def __init__(self, data=None):
        # Initialize this queue, and store data if it exists
        self.length = 0
        self.first = None
        self.last = None
        if data != None:
            self.enqueue(data)

    def enqueue(self, data):
        # Add data to the end of the queue
        newNode = Node(data)

        if self.length == 0:
            self.first = newNode
        else:
            self.last.next = newNode

        self.last = newNode
        self.length = self.length + 1

    def dequeue(self):
        # Return the data in the element at the beginning of the queue, or None if the queue is empty
        if self.length == 0:
            return None

        data = self.first.data
        self.first = self.first.next
        self.length = self.length - 1
        return data

    def __len__(self):
        # Return the number of elements in the stack
        return self.length


class MyStack:
    def __init__(self, data=None):
        # Initialize this stack, and store data if it exists
        self.length = 0
        self.last = None
        if data != None:
            self.push(data)

    def push(self, data):
        # Add data to the beginning of the stack
        newNode = Node(data)

        if self.last != None:
            newNode.prev = self.last
        self.last = newNode
        self.length = self.length + 1

    def pop(self):
        # Return the data in the element at the beginning of the stack, or None if the stack is empty
        if self.length == 0:
            return None

        data = self.last.data
        self.last = self.last.prev
        self.length = self.length - 1
        return data

    def __len__(self):
        # Return the number of elements in the stack
        return self.length


class Node:
    def __init__(self, data, next=None):
        # Initialize this node, insert data, and set the next node if any
        self.data = data
        self.next = next
        self.prev = None
