package adventofcode2022.problem10

const val input10 = """addx 1
addx 4
addx 1
noop
addx 4
noop
noop
noop
noop
addx 4
addx 1
addx 5
noop
noop
addx 5
addx -1
addx 3
addx 3
addx 1
noop
noop
addx 4
addx 1
noop
addx -38
addx 10
noop
noop
noop
noop
noop
addx 2
addx 3
addx -2
addx 2
addx 5
addx 2
addx -13
addx 14
addx 2
noop
noop
addx -9
addx 19
addx -2
addx 2
addx -9
addx -24
addx 1
addx 6
noop
noop
addx -2
addx 5
noop
noop
addx -12
addx 15
noop
addx 3
addx 3
addx 1
addx 5
noop
noop
noop
noop
addx -24
addx 29
addx 5
noop
noop
addx -37
noop
addx 26
noop
noop
addx -18
addx 28
addx -24
addx 17
addx -16
addx 4
noop
addx 5
addx -2
addx 5
addx 2
addx -18
addx 24
noop
addx -2
addx 10
addx -6
addx -12
addx -23
noop
addx 41
addx -34
addx 30
addx -25
noop
addx 16
addx -15
addx 2
addx -12
addx 19
addx 3
noop
addx 2
addx -27
addx 36
addx -6
noop
noop
addx 7
addx -33
addx -4
noop
addx 24
noop
addx -17
addx 1
noop
addx 4
addx 1
addx 14
addx -12
addx -14
addx 21
noop
noop
noop
addx 5
addx -17
addx 1
addx 20
addx 2
noop
addx 2
noop
noop
noop
noop
noop"""

val input10Test = """addx 15
addx -11
addx 6
addx -3
addx 5
addx -1
addx -8
addx 13
addx 4
noop
addx -1
addx 5
addx -1
addx 5
addx -1
addx 5
addx -1
addx 5
addx -1
addx -35
addx 1
addx 24
addx -19
addx 1
addx 16
addx -11
noop
noop
addx 21
addx -15
noop
noop
addx -3
addx 9
addx 1
addx -3
addx 8
addx 1
addx 5
noop
noop
noop
noop
noop
addx -36
noop
addx 1
addx 7
noop
noop
noop
addx 2
addx 6
noop
noop
noop
noop
noop
addx 1
noop
noop
addx 7
addx 1
noop
addx -13
addx 13
addx 7
noop
addx 1
addx -33
noop
noop
noop
addx 2
noop
noop
noop
addx 8
noop
addx -1
addx 2
addx 1
noop
addx 17
addx -9
addx 1
addx 1
addx -3
addx 11
noop
noop
addx 1
noop
addx 1
noop
noop
addx -13
addx -19
addx 1
addx 3
addx 26
addx -30
addx 12
addx -1
addx 3
addx 1
noop
noop
noop
addx -9
addx 18
addx 1
addx 2
noop
noop
addx 9
noop
noop
noop
addx -1
addx 2
addx -37
addx 1
addx 3
noop
addx 15
addx -21
addx 22
addx -6
addx 1
noop
addx 2
addx 1
noop
addx -10
noop
noop
addx 20
addx 1
addx 2
addx 2
addx -6
addx -11
noop
noop
noop"""