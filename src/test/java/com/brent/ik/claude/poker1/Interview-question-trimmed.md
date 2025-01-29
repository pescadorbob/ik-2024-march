Solve the following technical interview question in Java:

**Title**: Optimal Poker Chip Distribution System

**Initial Problem Statement**:
"A poker room needs to distribute chips to players 
at the start of a game. Design a system that determines 
the optimal distribution of poker chips to each player."

```
Given:
- A target buy-in amount (e.g., $100)
- Available chip denominations [25¢, 50¢, $1, $2, $5]
- Maximize the total number of chips each player gets
- Limited supply of each chip color
- Each chip color must be assigned exactly one denomination
- Each denomination must be assigned exactly one color
- don't use a greedy approach choosing chips, instead use dynamic programming.
- The chip denominations aren't specified to a color, you have to try all the combinations of chip color to denominations to maximize the number of chips each player receives.
- All players need the same distribution

Input Example:
- Target buy-in: $31
- Number of players: 10
- Available chips:
  * White chips: 149
  * Red chips: 143
  * Blue chips: 98
  * Green chips: 50
  * Black chips: 46
- Possible denominations: [25¢, 50¢, $1, $2, $5, $10]

Task: 
1. Assign denominations to chip colors
2. Find the distribution that maximizes chips per player
3. Ensure enough physical chips for all players
```


**Evaluation Criteria**:

1. **Problem Solving Approach**:
    - Breaking down the problem into manageable components
    - Identifying constraints and edge cases
    - Progressive optimization

2. **Technical Design**:
    - Data structure choices
    - Algorithm efficiency
    - Handling of constraints

3. **Code Quality**:
    - Clean, maintainable code
    - Error handling
    - Testing considerations

4. **System Design Considerations**:
    - Scalability
    - Performance
    - Maintainability

**Key Areas to Probe**:

1. **Algorithm Knowledge**:
    - Dynamic programming understanding
    - Combinations and permutations
    - Optimization techniques
    - Constraint satisfaction

2. **Problem-Solving Skills**:
    - How they break down complex problems
    - Handling of edge cases
    - Optimization approaches

3. **Code Organization**:
    - Class/method structure
    - Separation of concerns
    - Error handling

**Red Flags to Watch For**:
1. Jumping straight to coding without analyzing the problem
2. Not considering physical chip constraints
3. Missing edge cases (like insufficient chips)
4. Not optimizing for maximum chips per player

**Green Flags**:
1. Methodical problem breakdown
2. Clear identification of constraints
3. Discussion of trade-offs
4. Consideration of real-world scenarios
5. Testing strategy discussion

