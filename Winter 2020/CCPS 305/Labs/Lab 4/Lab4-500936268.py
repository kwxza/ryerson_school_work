# You may not use dicts.
def getName():
    return "Kipusi, Kasaine"


class MyHashTable():
    def __init__(self, size, hash1):
        # Create an empty hashtable with the size given, and stores the function hash1

        self.size = size
        self.hash1 = hash1
        # The hash table is created as a list of
        # the given size, with each item of the
        # list initialized to None
        self.table = [None] * self.size
        self.length = 0

    def put(self, key, data):
        # Store data with the key given, return true if successful or false if the data cannot be entered
        # On a collision, the table should not be changed

        # Data is only stored in the table if the
        # hashed index corresponds to a None value.
        index = self.hash1(key)

        if self.table[index] is None:
            self.table[index] = key, data
            # Length (entry count) is updated as
            # data is inserted.
            self.length += 1
            return True

        return False

    def get(self, key):
        # Returns the item linked to the key given, or None if element does not exist

        index = self.hash1(key)

        if self.table[index] is not None:
            existingKey, data = self.table[index]
            if key == existingKey:
                return data

        return None

    def __len__(self):
        # Returns the number of items in the Hash Table

        return self.length

    def isFull(self):
        # Returns true if the HashTable cannot accept new members

        # The table is full if its length (number
        # of entries) matches its size.
        return self.length == self.size


class MyChainTable(MyHashTable):
    def __init__(self, size, hash1):
        # Create an empty hashtable with the size given, and stores the function hash1
        super().__init__(size, hash1)

        # For the chained hash table, each
        # entry is initialized to an empty
        # list that will hold all keys
        # corresponding to an index.
        for cell in range(size):
            self.table[cell] = []

    def put(self, key, data):
        # Store the data with the key given in a list in the table, return true if successful or false if the data cannot be entered
        # On a collision, the data should be added to the list

        # The put() method returns false if the
        # key already exists in the table.
        # For the hashed index, each existing entry
        # in its chain must be checked to see if
        # the given key already exists.

        index = self.hash1(key)

        for existingKey, _ in self.table[index]:
            if key == existingKey:
                return False

        self.table[index].append((key, data))
        self.length += 1
        return True

    def get(self, key):
        # Returns the item linked to the key given, or None if element does not exist

        index = self.hash1(key)

        # The keys in the chain of the hashed index
        # are iterated to see if one matches the
        # given key.
        for existingKey, data in self.table[index]:
            if key == existingKey:
                return data

        return None

    def __len__(self):
        # Returns the number of items in the Hash Table
        return self.length

    def isFull(self):
        # Returns true if the HashTable cannot accept new members
        return False


class MyDoubleHashTable(MyHashTable):
    def __init__(self, size, hash1, hash2):
        # Create an empty hashtable with the size given, and stores the functions hash1 and hash2
        super().__init__(size, hash1)
        self.hash2 = hash2

    def put(self, key, data):
        # Store data with the key given, return true if successful or false if the data cannot be entered
        # On a collision, the key should be rehashed using some combination of the first and second hash functions
        # Be careful that your code does not enter an infinite loop

        index = self.hash1(key)
        offset = self.hash2(key)
        rejected = [False] * self.size

        # Indexes that have already been visited will
        # be cached in the rejected[] list.
        # This is because if an index is re-visited
        # after being rejected, then every offset
        # step from that index has also already been
        # visited and rejected.
        # This will stop the below while-loop from
        # executing infinitely.
        # Insertion is also not attempted if the
        # table is already full.

        while not rejected[index] \
                and not self.isFull():

            if self.table[index] is None:
                # The key is stored with the data to
                # allow for accurate data retrieval.
                self.table[index] = key, data
                self.length += 1
                return True

            else:
                rejected[index] = True
                if offset > index:
                    index = self.size - (offset - index)
                else:
                    index -= offset

        return False

    def get(self, key):
        # Returns the item linked to the key given, or None if element does not exist

        index = self.hash1(key)
        offset = self.hash2(key)
        rejected = [False] * self.size

        # The get() method also caches rejected
        # indices to determine a stopping condition
        # and prevent infinite looping.

        while not rejected[index] \
                and self.table[index] is not None:

            existingKey, data = self.table[index]

            if key == existingKey:
                return data

            else:
                rejected[index] = True
                if offset > index:
                    index = self.size - (offset - index)
                else:
                    index -= offset

        return None

    def __len__(self):
        # Returns the number of items in the Hash Table
        return self.length
