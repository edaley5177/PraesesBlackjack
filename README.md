# Blackjack Simulator

A command-line, text-based Java application that simulates a game of Blackjack featuring custom player setups, dynamic bot strategies, and full betting mechanics.

---

## 🚀 Quick Start

1. **Extract the project files** (if downloaded as a `.zip` archive).
2. **Navigate to the source directory:**
   ```bash
   cd src
3. javac BlackJackSimulator.java
4. java BlackJackSimulator
5. Follow the on-screen prompts to start playing.

Game Overview & Features
Text-Based Interface: All interaction takes place via standard command-line prompts.

Custom Deck Selection: Choose the number of decks used in the shoe.

Player Configuration:
Supports 1 human player with room for future multi-player expansions.
Generates automated bot players based on the total number of decks selected.

Bot Strategy: Bots execute pre-programmed rules designed to optimize decisions against the dealer.

Dealer Rules: The dealer hits on any hand total under 17 and stands on 17 or higher.

Betting & Rules
Payouts:
Standard winning hands payout 1:1 (even money).

Blackjack pays 3:2 (provided the dealer does not also have Blackjack).

Splitting Mechanics:
Allowed only on matching pair cards (e.g., [J, J], but not [J, K]).
Resplitting a previously split hand is currently not permitted.
