fn main()
{
    let string1 = String::from("Hello ");
    let string2 = String::from("world!");
    let constr = string_concat(string1, string2);
    println!("{}", constr);

    let floats = [1.0, 2.0, 3.0, 4.0, 5.0];
    let bounds = (0,3);
    let avg = sub_array_average(&floats, bounds);
    println!("Average is {}", avg);

    let mut ints = [1,3,-1,0,-7];
    let result = array_signum(&mut ints);
    println!("{:?}", result);
}

fn string_concat (s1: String, s2: String) -> String
{
    format!("{}{}", &s1, &s2)
}

fn sub_array_average (arr: &[f64], tup: (usize, usize)) -> f64
{
    let mut sum: f64 = 0.0;
    let mut index = tup.0;

    while index < tup.1
    {
        sum += arr[index];
        index += 1;
    }

    let divisor: f64 = tup.1 as f64 - tup.0 as f64;
    sum / divisor
}

fn array_signum (arr: &mut [i32]) -> &[i32]
{
    let end = arr.len() as usize;
    let mut i: usize = 0;
    loop
    {
        if i == end {break};

        if arr[i] < 0 {arr[i] = -1}
        else if arr[i] > 0 {arr[i] = 1}
        else {arr[i] = 0};

        i += 1;
    }

    arr
}


