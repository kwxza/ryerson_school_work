defmodule Poker do

    # The deal function takes the input deck,
    # deals the cards out to two hands, 
    # sorts the cards and then calls the
    # analytical functions.
    # Once a winner has been found, 
    # the format_card function is used to 
    # map the list to the correct output.   
    def deal(deck) do
        deck = Enum.take(deck, 10)

        hand1 = Stream.take_every(deck, 2) |> Stream.map(&(find_rank_and_suit(&1))) |> Enum.sort()
        hand2 = (tl deck) |> Stream.take_every(2) |> Stream.map(&(find_rank_and_suit(&1))) |> Enum.sort()

        hand1_score = check_flush(hand1) + check_straight(hand1) + check_duplicates(hand1)
        hand2_score = check_flush(hand2) + check_straight(hand2) + check_duplicates(hand2)

        winner = cond do
            hand1_score > hand2_score -> hand1
            hand1_score < hand2_score -> hand2
            hand1_score == hand2_score -> case high_card(hand1, hand2) do
                {1,0} -> hand1
                {0,1} -> hand2
                _ -> case high_suit(hand1, hand2) do
                    true -> hand1
                    false -> hand2
                end
            end
        end

        winner |> Enum.map(&(format_card(&1)))
    end

    # Function used to map card from deck to its rank and suit values
    defp find_rank_and_suit(card) do
        unless rem(card, 13) == 0 do
            {rem(card, 13), trunc(card/13)}
        else
            {13, trunc(card/13) - 1}
        end
    end


    # CHECKING FOR FLUSHNESS - Recursive
    ###################################################
    # Recursive check for flush - header w/ default args
    defp check_flush(hand, flush_suit \\ nil, is_flush \\ true)
    # Initial call to function with no suit set to check for flushness
    # Suit to check for flushness set to 2 element of first tuple in hand list
    defp check_flush(hand, flush_suit, is_flush) when is_nil(flush_suit) do
        flush_suit = elem hd(hand), 1
        check_flush(tl(hand), flush_suit, is_flush)
    end
    # If call made with is_flush boolean as false, score of 0 returned
    defp check_flush(_hand, _flush_suit, is_flush) when is_flush == false do
        0
    end
    # If the entire list has been traversed, return score of 8
    defp check_flush([], _flush_suit, _is_flush) do
        8
    end
    # Check the head of the remaining list against the
    # suit that's being tracked for flushness.
    # @is_flush will be set to false if they are unequal.
    # Call check_flush function again with tail and is_flush
    defp check_flush(hand, flush_suit, _is_flush) do
        is_flush = flush_suit == elem hd(hand), 1
        check_flush(tl(hand), flush_suit, is_flush)
    end


    # CHECKING FOR STRAIGHTNESS - Recursive
    #######################################
    # Header with default arguments
    defp check_straight(hand, acc \\ nil, is_straight \\ true)
    # Sets the accumulator when the function is first called
    defp check_straight(hand, acc, is_straight) when is_nil(acc) do
        acc = elem hd(hand), 0
        check_straight(tl(hand), acc + 1, is_straight)
    end
    # If hand is found to be not straight, return score of zero
    defp check_straight(_hand, _acc, is_straight) when is_straight == false do
        0
    end
    # Ace-low/high scenario -> Ace-high returns higher score
    defp check_straight([{13,_}], acc, _is_straight) do
        case acc do
            6 -> 6
            13 -> 7
            _ -> 0
        end
    end
    # Once the entire list has been traversed, return score
    defp check_straight([], _acc, _is_straight) do
        6
    end
    # Check if the first element in the list matches the
    # accumulator, which is being incremented by 1 with
    # each recursive call to check_straight
    defp check_straight(hand, acc, _is_straight) do
        is_straight = acc == elem hd(hand), 0
        check_straight(tl(hand), acc + 1, is_straight)
    end


    # CHECKING DUPLICATES
    ##############################
    # Pattern match possible duplicate combinations to return a score
    defp check_duplicates(hand) do
       case hand do
            # Four of a kind
            [{x,_},{x,_},{x,_},{x,_},{_,_}] -> 10 
            [{_,_},{x,_},{x,_},{x,_},{x,_}] -> 10
            # Full house
            [{x,_},{x,_},{x,_},{y,_},{y,_}] -> 9
            [{x,_},{x,_},{y,_},{y,_},{y,_}] -> 9
            # Three of a kind
            [{x,_},{x,_},{x,_},{_,_},{_,_}] -> 5
            [{_,_},{x,_},{x,_},{x,_},{_,_}] -> 5
            [{_,_},{_,_},{x,_},{x,_},{x,_}] -> 5
            # Two pairs
            [{x,_},{x,_},{y,_},{y,_},{_,_}] -> 4
            [{x,_},{x,_},{_,_},{y,_},{y,_}] -> 4
            [{_,_},{x,_},{x,_},{y,_},{y,_}] -> 4
            # One pair
            [{x,_},{x,_},{_,_},{_,_},{_,_}] -> 2
            [{_,_},{x,_},{x,_},{_,_},{_,_}] -> 2
            [{_,_},{_,_},{x,_},{x,_},{_,_}] -> 2
            [{_,_},{_,_},{_,_},{x,_},{x,_}] -> 2
            # Anything else
            [{_,_},{_,_},{_,_},{_,_},{_,_}] -> 0 
       end
    end


    # TIE BREAK WITH HIGH CARD
    # Returns 2-value tuple 
    # {1,0} - hand1 is winner
    # {0,1} - hand2 is winner
    # {0,0} - still tied, go to high_suit
    ########################################
    # Hands are ordered in ascending order, so
    # reverse to start checking in descending
    # order of rank.
    defp high_card(hand1, hand2) do
        score = {0,0}
        high_card(Enum.reverse(hand1), Enum.reverse(hand2), score)
    end
    # If all cards have been compared, return score
    defp high_card([], [], score) do
        score
    end
    # If a score has been found, return score
    defp high_card(_hand1, _hand2, score) when score != {0,0} do
        score
    end
    # If score is still tied at zero, compare ranks
    # of card tuples represented by head1/head2.
    # Set the score based on the comparison
    # and call high_card again.
    defp high_card([head1|tail1], [head2|tail2], score) when score == {0,0} do
        score = case elem(head1,0) == elem(head2,0) do
            true -> {0,0}
            false -> case elem(head1,0) > elem(head2,0) do
                true -> {1,0}
                false -> {0,1}
            end
        end
        high_card(tail1, tail2, score)
    end


    # TIE BREAK WITH HIGH SUIT
    ######################################
    # This function only runs if the 2 hands have been
    # found to be identical in rank.
    # It is only necessary to compare the suits of the 
    # highest cards of each hand. 
    # This is because two cards of identical rank MUST 
    # have different suits.
    # Returns true if hand1 has the high suit, otherwise false.
    defp high_suit(hand1, hand2) do
        elem(Enum.fetch!(hand1, 4), 1) > elem(Enum.fetch!(hand2, 4), 1)
    end


    # Function to format card tuple to string output
    defp format_card({rank, suit}) do
        suit_decoder = {"C", "D", "H", "S"}
        cond do
            rank == 13 -> Integer.to_string(1) <> elem(suit_decoder, suit)
            true -> Integer.to_string(rank + 1) <> elem(suit_decoder, suit)
        end       
    end
end