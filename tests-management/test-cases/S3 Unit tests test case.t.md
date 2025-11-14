# S3 Unit tests test case
id: S3
tags: #unit-tests #test-cases

C1 Position tests
id: C1
* Test getRow() returns the correct row
* Test getColumn() returns the correct column
* Test equals() for equal and different positions
* Test isAdjacentTo() for adjacent and non-adjacent positions
* Test occupy() and isOccupied()
* Test shoot() and isHit()

C2 Compass tests
id: C2
* Test getDirection() returns the correct char for each direction
* Test toString() returns the correct string for each direction
* Test charToCompass() with valid chars (n, s, e, o)
* Test charToCompass() with invalid chars (x, space, digit)
* Test charToCompass() with uppercase chars (N, S, E, O)

