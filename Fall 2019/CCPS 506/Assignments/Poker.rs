use std::collections::HashMap;

//Card and Hand structs
#[derive(Debug)]
#[derive(Eq)]
#[derive(PartialEq)]
#[derive(PartialOrd)]
#[derive(Ord)]
#[derive(Copy)]
#[derive(Clone)]
struct Card {
    rank: i32,
    suit: i32,
}

struct Hand {
    cards: [Card; 5],
    score: i32,
}

// Main function
fn main () {
}


// Functions

fn deal (deck: &[i32; 52]) -> [String; 5] {

    // Initializing Card arrays that accept the dealt cards and
    // are then used to initalize the Hand structs for analysis.
    let mut deal1: [Card; 5] = [Card {rank: -1, suit: -1,},Card {rank: -1, suit: -1,},Card {rank: -1, suit: -1,},Card {rank: -1, suit: -1,},Card {rank: -1, suit: -1,}];
    let mut deal2: [Card; 5] = [Card {rank: -1, suit: -1,},Card {rank: -1, suit: -1,},Card {rank: -1, suit: -1,},Card {rank: -1, suit: -1,},Card {rank: -1, suit: -1,}];

    // Dealing the deck, alternating by card.
    for index in 0..10 {
        if index % 2 == 0 {
            deal1[index / 2].rank = deck[index];
        } else {
            deal2[index / 2].rank = deck[index];
        }
    }    

    // Initializing Hand structs
    let mut hand1 = Hand {cards: deal1, score: 0,};
    let mut hand2 = Hand {cards: deal2, score: 0,};

    find_rank_and_suit (&mut hand1);
    find_rank_and_suit (&mut hand2);

    check_straight_and_flush (&mut hand1);
    check_straight_and_flush (&mut hand2);

    // It's impossible for a hand that is straight or flush
    // to have duplicate rank cards, so rank duplicates are
    // only checked if no score has been assigned.
    if hand1.score == 0 {       
        check_duplicates (&mut hand1);
    }
    if hand2.score == 0 {       
        check_duplicates (&mut hand2);
    }

    // After analyzing the hands
    if hand1.score == hand2.score {
        tie_break (&mut hand1, &mut hand2);
    }

    winner (&hand1, &hand2)
}

fn find_rank_and_suit (hand: &mut Hand) {

    // Processing each card of the hand
    for index in 0..5 {

        // Dividing by 13 will give a value representing the card's suit.
        // 0 = Clubs, 1 = Diamonds, 2 = Hearts, 3 = Spades
        if hand.cards[index].rank % 13 == 0 {
            hand.cards[index].suit = hand.cards[index].rank / 13 - 1; // Adjusting for ace
        } else {
            hand.cards[index].suit = hand.cards[index].rank / 13;
        }

        // The remainder will be the rank of the card.
        if hand.cards[index].rank % 13 == 0 {
            hand.cards[index].rank = 13; // Adjusting for ace
        } else {
            hand.cards[index].rank = hand.cards[index].rank % 13;
        }
    }

    // Putting the card array into sorted order based
    // on first value of each card tuple (rank value).
    hand.cards.sort();
}

fn check_straight_and_flush (hand: &mut Hand) {

    // Checking if straight and assigning a score of 
    // 6 or 7 if it is an ace-high straight.

    let mut straight_score = 6;
    // For the ace-high scenario:
    if hand.cards[0].rank == 9 && hand.cards[4].rank == 13 {
        straight_score = 7;
    }

    let mut straight_count = 4;
    // For ace-low scenario:
    if hand.cards[0].rank == 1 && hand.cards[4].rank == 13 {
        straight_count = 3;
    }

    let straight = hand.cards[0].rank + straight_count;

    if straight == hand.cards[straight_count as usize].rank {
        hand.score += straight_score;
    }

    // Checking if flush
    let flush_score = 8;
    let flush_suit = hand.cards[0].suit;
    let mut flush = true;
    let mut index = 1;

    while flush && index < 5 {
        if hand.cards[index].suit != flush_suit {
            flush = false;
        }
        index += 1;
    }

    if flush {
        hand.score += flush_score;
    }
}

fn check_duplicates (hand: &mut Hand) {

    let four_score = 10;
    let full_house_score = 9;
    let three_score = 5;
    let two_pair_score = 4;
    let pair_score = 2;

    // Use HashMap to track number of duplicate occurrences
    let mut duplicates: std::collections::HashMap<i32, i32> = HashMap::new();

    for card in &hand.cards {
        let update = duplicates.entry(card.rank).or_insert(0);
        *update += 1;
    }

    // Remove single occurrences from the map
    duplicates.retain(|_, &mut occurrences| occurrences != 1);

    // Sum the values of duplicate occurrences
    let mut duplicate_sum = 0;
    for val in duplicates.values() {
        duplicate_sum += val;
    }

    // duplicate_sum is equal to the number of cards left after discarding singles.
    // duplicates.len() gives the number of groups of duplicate cards.
    // These two values can be used to assign a score to the hand.
    hand.score += match (duplicates.len(), duplicate_sum) {
        (1, 4) => four_score,
        (2, 5) => full_house_score,
        (1, 3) => three_score,
        (2, 4) => two_pair_score,
        (1, 2) => pair_score,
        (_, _) => 0
    };
}

fn tie_break (hand1: &mut Hand, hand2: &mut Hand) {

    let tie_break_score = 1;
    let mut found = false;
    let mut end_of_index = false;
    let mut index = 4;

    // Check high card
    while !found && !end_of_index {
        let rank_difference = hand1.cards[index].rank - hand2.cards[index].rank;
        match rank_difference.signum() {
            1 => {
                hand1.score += tie_break_score;
                found = true;
            },
            -1 => {
                hand2.score += tie_break_score;
                found = true;
            },
            _ => {
                if index == 0 {
                    end_of_index = true;
                } else { 
                    index -= 1; 
                }
            },
        } 
    }

    // If no high card, reset index and check suits
    index = 4;
    while !found {
        let suit_difference = hand1.cards[index].suit - hand2.cards[index].suit;
        match suit_difference.signum() {
            1 => {
                hand1.score += tie_break_score;
                found = true;
            },
            -1 => {
                hand2.score += tie_break_score;
                found = true;
            },
            _ => {
                index -= 1;
            },
        }
    }
}

fn winner (hand1: &Hand, hand2: &Hand) -> [String; 5] {
    
    // Array to translate Card struct's suit value 
    // from integer to character representation
    let suit_decoder = ['C','D','H','S'];

    let winner = match hand1.score > hand2.score {
        true => hand1.cards,
        false => hand2.cards,
    };

    let mut output: [String; 5] = [String::new(),String::new(),String::new(),String::new(),String::new()];

    for index in 0..5 {
        if winner[index].rank == 13 {
            output[index] = format!("{}{}", 1, suit_decoder[winner[index].suit as usize]); 
        } else {
            output[index] = format!("{}{}", winner[index].rank + 1, suit_decoder[winner[index].suit as usize]);
        }
    }

    output
}