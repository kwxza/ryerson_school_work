# You may not use dicts.
def getName():
    return "Last name, first name"


class MyHashTable():
    def __init__(self, size, hash1):
        # Create an empty hashtable with the size given, and stores the function hash1
        pass

    def put(self, key, data):
        # Store data with the key given, return true if successful or false if the data cannot be entered
        # On a collision, the table should not be changed
        pass

    def get(self, key):
        # Returns the item linked to the key given, or None if element does not exist
        pass

    def __len__(self):
        # Returns the number of items in the Hash Table
        pass

    def isFull(self):
        # Returns true if the HashTable cannot accept new members
        pass


class MyChainTable(MyHashTable):
    def __init__(self, size, hash1):
        # Create an empty hashtable with the size given, and stores the function hash1
        super().__init__(size, hash1)
        pass

    def put(self, key, data):
        # Store the data with the key given in a list in the table, return true if successful or false if the data cannot be entered
        # On a collision, the data should be added to the list
        pass

    def get(self, key):
        # Returns the item linked to the key given, or None if element does not exist
        pass

    def __len__(self):
        # Returns the number of items in the Hash Table
        pass

    def isFull(self):
        # Returns true if the HashTable cannot accept new members
        pass


class MyDoubleHashTable(MyHashTable):
    def __init__(self, size, hash1, hash2):
        # Create an empty hashtable with the size given, and stores the functions hash1 and hash2
        super().__init__(size, hash1)
        pass

    def put(self, key, data):
        # Store data with the key given, return true if successful or false if the data cannot be entered
        # On a collision, the key should be rehashed using some combination of the first and second hash functions
        # Be careful that your code does not enter an infinite loop
        pass

    def get(self, key):
        # Returns the item linked to the key given, or None if element does not exist
        pass

    def __len__(self):
        # Returns the number of items in the Hash Table
        pass
