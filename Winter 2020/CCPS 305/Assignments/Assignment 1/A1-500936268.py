def getName():
    return "Kipusi, Kasaine"


class Pathfinder():
    def __init__(self, vector):
        # Initialize the Pathfinder object
        self.vector = vector
        self.paths = []
        self.findAllPaths(0, [])

    def findAllPaths(self, position, solution):
        # Recursively explore the possible paths and store valid paths
        # This method will not be tested, so you can modify the parameters as needed

        # First check to see if the position is already
        # in the path of the current possible solution.
        # If not, append it to the possible solution list.
        if position not in solution:
            solution.append(position)

            # If the position is at the end of the vector,
            # a full path solution has been found.
            # Thus, attach the solution to the paths class variable.
            if position == len(self.vector) - 1:
                self.paths.append(solution)
            else:
                # Find the position(s) that can be moved to
                # from the current position.
                forwardPos = position + self.vector[position]
                backwardPos = position - self.vector[position]

                # If the found position(s) are within the bounds
                # of the vector, call findAllPaths with the
                # new position(s) and a shallow copy of the
                # possible solution list (to avoid passing the
                # same solution list for each position and thus
                #  modifying the same list concurrently).
                if forwardPos < len(self.vector):
                    self.findAllPaths(forwardPos, solution.copy())
                if 0 < backwardPos:
                    self.findAllPaths(backwardPos, solution.copy())

    def getPaths(self):
        # Return the list of viable paths, or [] if there are no solutions
        return self.paths
