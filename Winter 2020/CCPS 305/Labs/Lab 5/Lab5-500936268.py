def getName():
    return "Kipusi, Kasaine"


class MyHeap():
    def __init__(self, array):
        # Create a heap from the passed array. The first element should be None.

        self.heap = [None] + array

        # The for-loop starts at the index of the first
        # parent node and iterates backwards over each
        # parent node, heapifying each node as it goes.
        # The heapify() method pushes the node at
        # the given index downwards until it reaches
        # its appropriate position within the heap.
        for index in range(len(self) // 2, 0, -1):
            self.heapify(index)

    def insert(self, data):
        # The insert() method inserts new data at the end of
        # the heap array and then compares the new data
        # to its parent by calling heapify() with the
        # index of the parent node.
        # heapify() will swap the nodes if necessary,
        # and will then be called again upon the parent
        # of the new position.
        # This process continues until the root is reached.

        self.heap.append(data)
        index = len(self) // 2

        while index > 0:
            self.heapify(index)
            index //= 2

    def heapify(self, index):
        # heapify() takes an index  that corresponds
        # to a node in the heap array.
        # The method determines if the node has a child
        # larger than itself. If so, the positions of the
        # node and its child are swapped.
        # The process repeats by checking against the
        # children of the new position and swapping if
        # necessary until the node reaches its correct
        # position within the heap.

        if index < 1:
            return

        left, right, greater, newIndex = None, None, None, None
        leftIndex, rightIndex = 2*index, 2*index+1

        try:
            left = self.heap[leftIndex]
            right = self.heap[rightIndex]
        except:
            pass

        if right is not None:
            greater, newIndex = (left, leftIndex) if left > right \
                else (right, rightIndex)
        elif left is not None:
            greater, newIndex = left, leftIndex
        else:
            return

        if greater > self.heap[index]:
            self.heap[newIndex] = self.heap[index]
            self.heap[index] = greater
            return self.heapify(newIndex)

    def extractMax(self):
        # The extractMax() method removes the first element
        # in the heap array as the max to be returned.
        # Before returning the max element, the method
        # removes the last element in the heap array and
        # places it into the root position.
        # The heapify() method is then called on the
        # root position, so that the element at that
        # index will be pushed downwards in the heap
        # until it reaches its correct position.

        max = None

        try:
            max = self.heap[1]
            self.heap[1] = self.heap.pop()
            self.heapify(1)
        except:
            pass

        return max

    def __len__(self):
        # Return the number of items currently in the heap
        return len(self.heap) - 1

    def getData(self):
        # Return the current heap as an array that does not use the first value
        return self.heap
