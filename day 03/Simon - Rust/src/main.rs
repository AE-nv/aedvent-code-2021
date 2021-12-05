use std::fs;

fn read_data(path: &str) -> Vec<String>{
    let contents = fs::read_to_string(path)
        .expect("Something went wrong reading the file");

    let numbers: Vec<String> = contents
        .split_terminator("\n")
        .map(|s| s.to_string())
        .collect();

    return numbers;
}

fn bit_to_decimal(mut input: Vec<i32>) -> i32 {
    let bits:u32 = (input.len()) as u32;
    let base: i32 = 2;
    let mut result: i32 = 0;
    for i in 0..bits{
        result += input.pop().unwrap() * base.pow(i);
    }
    return result;
}

fn calculate_bits_place(data : &Vec<String>, place : usize) -> Vec<i32>{
    let mut sum_zero: i32 = 0;
    let mut sum_one: i32 = 0;
    for s in 0..data.len(){
        let bit: char = data.get(s).unwrap().chars().nth(place).unwrap();
        if bit == '0'{
            sum_zero += 1;
        }else{
            sum_one += 1;
        } }
    let mut result = Vec::new();
    result.push(sum_one);
    result.push(sum_zero);
    return result;
}


fn find_power_consumption(data: Vec<String>) -> i32{
    let mut gamma: Vec<i32> = Vec::new();
    let mut epsilon: Vec<i32> = Vec::new();
    for i in 0..data.get(0).unwrap().len(){
        let ammout_of_bits_in_place = calculate_bits_place(&data, i);
        if ammout_of_bits_in_place.get(0) < ammout_of_bits_in_place.get(1){
            gamma.push(0);
            epsilon.push(1);
        } else{
            gamma.push(1);
            epsilon.push(0);
        }
    }
    println!("gamma: {:?}\nepsilon: {:?}", gamma, epsilon);
    let gamma = bit_to_decimal(gamma);
    let epsilon = bit_to_decimal(epsilon);

    println!("gamma: {}\nepsilon: {}", gamma, epsilon);
    return gamma*epsilon;
}

fn filter_out_bits_on_loc(data: &mut Vec<String>, bit : &String, loc: i32){
    data.retain(|x| x.chars().nth(loc as usize) == Some(bit.chars().next().unwrap()));
}

fn find_oxygen_rating(data : &Vec<String>) -> Vec<i32>{
    let mut possible_values:Vec<String> = (*data.iter().map(|s| s.clone()).collect::<Vec<String>>()).to_vec();

    let mut i = 0;
    while i <data.get(0).unwrap().len() && possible_values.len()>1{
        let ammout_of_bits_in_place = calculate_bits_place(&possible_values, i);
        if ammout_of_bits_in_place.get(0) >= ammout_of_bits_in_place.get(1){
            filter_out_bits_on_loc(&mut possible_values, &String::from("1"), i as i32 );
        }else {
            filter_out_bits_on_loc(&mut possible_values, &String::from("0"), i as i32 );
        }
        i +=1;
    }
    let oxygen_solution= possible_values.get(0).unwrap();
    let oxygen_solution: Vec<i32>= oxygen_solution.chars().map(|c| c as i32 - 0x30).collect();
    return oxygen_solution;
}

fn find_co2_rating(data : &Vec<String>) -> Vec<i32>{
    let mut possible_values:Vec<String> = (*data.iter().map(|s| s.clone()).collect::<Vec<String>>()).to_vec();
    let mut i = 0;
    while i <data.get(0).unwrap().len() && possible_values.len()>1{
        let ammout_of_bits_in_place = calculate_bits_place(&possible_values, i);
        if ammout_of_bits_in_place.get(0) < ammout_of_bits_in_place.get(1){
            filter_out_bits_on_loc(&mut possible_values, &String::from("1"), i as i32 );
        } else {
            filter_out_bits_on_loc(&mut possible_values, &String::from("0"), i as i32 );
        }
        i +=1;
    }

    let co2_solution= possible_values.get(0).unwrap();
    let co2_solution: Vec<i32>= co2_solution.chars().map(|c| c as i32 - 0x30).collect::<Vec<i32>>();
    // possible_values.iter().map(|(s, co2_solution)|co2_solution.push(s.chars.enumerate.parse().expect("parse error"))).collect();
    return co2_solution;
}

fn find_life_support_rating(data: Vec<String>) -> i32{
    let oxygen = find_oxygen_rating(&data);
    let co2 = find_co2_rating(&data);

    println!("oxygen: {:?}\nco2: {:?}", oxygen, co2);
    let oxygen = bit_to_decimal(oxygen);
    let co2 = bit_to_decimal(co2);

    println!("oxygen: {}\nco2: {}", oxygen, co2);
    return oxygen*co2;
}



fn main() {
    let input :Vec<String> = read_data("./data/input.txt");
    let solution = find_life_support_rating(input);
    println!("solution: {}",solution);
}

