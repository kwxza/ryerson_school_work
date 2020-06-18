fn main () {
    struct Card {
        rank: i32,
        suit: i32,
    }

    struct Hand {
        cards: [Card; 5],
        score: i32,
    }

    let mut deal1: [Card; 5] = [Card {rank: 0, suit: 0,},Card {rank: 0, suit: 0,},Card {rank: 0, suit: 0,},Card {rank: 0, suit: 0,},Card {rank: 0, suit: 0,}];

    deal1[0] = Card {rank: 1, suit: 0};

    println!("deal1: {:?}", deal1[0].suit);


    let mut hand1 = Hand {
        cards: deal1,
        score: 0,
    };
}