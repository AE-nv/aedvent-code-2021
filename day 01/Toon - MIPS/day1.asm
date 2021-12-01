# To run, get mips mars simulator from:
# https://courses.missouristate.edu/KenVollmar/mars/download.htm
# and open this file within the editor
.data 
	input_file:		.asciiz "C:\\Users\\ToonDeburchgrave\\Dev\\aedvent-code-2021\\day 01\\Toon - MIPS\\input.txt"
	amount_of_numbers: 	.word 	2000
	separator:		.asciiz	"\r"
	alignment: 		.space 	2	# make sure number_list pointer aligns to word boundary
	number_list: 		.space	2000
	buffer:			.space 	12000
.text

main:
	j	day1
	
day1:
	jal	read_file
	jal	parse_file
	jal	count_increases
	move 	$a0	$v0	# set result to a0
	jal	print_result
	jal 	count_windowed_increases
	move 	$a0	$v0	# set result to a0
	jal 	print_result
	li	$v0	10	# syscall exit
	syscall
	
	
read_file:
# open file
	li	$v0 	13		# open file syscode
	la	$a0	input_file	# address of filename
	li	$a1	0		# flag, read
	li	$a2	0		# mode, ignored
	syscall
	move	$s6	$v0		# file descriptor saved in s6
# read all data from file
	li	$v0	14		# read from file syscode
	move	$a0	$s6		# file descriptor
	la	$a1	buffer		# location of input
	li	$a2	12000		# amount of bytes to read
	syscall
	move 	$s7	$v0 	# save amount of characters in s7
	
	li   	$v0	16      # system call for close file
	move 	$a0	$s6    	# file descriptor to close
	syscall               	# close file
	
	jr	$ra

# turn ascii into integers
parse_file:
	addi	$sp	$sp	-4
	sw	$ra	($sp)		# store return address
	
	la	$a0	buffer 		# set a0 to start buffer
	la 	$a1	number_list     # set a1 to location of the "interpreted list"
	li	$a2	0		# set a2 to 0
	lw	$a3	amount_of_numbers
	j	read_character_start
	
# read one character
# a0 = location of character to read
# a1 = location of next empty location in number_list
# a2 = value up until now
# a3 = amount of numbers left
read_character_start:
	lw	$t1	separator	# get separator
read_character:
	lb	$t0	($a0)	# load next character
	beq	$t0	$t1	read_character_end	#if separator
	
	li	$t2	10		# load 10
	mul	$a2	$a2	$t2	# multiply current by 10
	andi	$t0	$t0	0x0F	# turn ascii into number
	add	$a2	$a2	$t0	# add the new value
	
	addi	$a0	$a0	1
	j	read_character		# do next character
	
read_character_end:
	sw	$a2	($a1)		# store total number in number_list
	addi	$a3	$a3 	-1	# sub 1 from amount of numbers left
	beqz	$a3	all_characters_read # end if 
	
	addi	$a1	$a1	4	# next location in number_list to v1
	addi	$a0	$a0	2	# next location of character to read is 2 further (/r/n)
	li	$a2	0
	j	read_character
	
all_characters_read:
	jr	$ra

# part 1
count_increases:
	la	$a0	number_list	# load a0
	lw	$a1	($a0)		# load a1 with first number
	addi	$a0	$a0	4	# next value location
	lw 	$a2	amount_of_numbers	# load a2
	addi 	$a2	$a2	-1	# because first number already 'read', go one less
	li	$v0	0		# result = 0

# a0 = current value location
# a1 = previous value
# a2 = amount of numbers left
# v0 = result: amount of increases
count_increases_loop:
	lw	$t0	($a0)		# load current value in t0
	ble	$t0	$a1	count_increase_no # if no increase jump forward
	addi	$v0	$v0	1	# add 1 to amount of increases
count_increase_no:
	move	$a1	$t0		# previous value = current value
	addi	$a0	$a0	4	# next value location
	addi 	$a2	$a2	-1	# subtract 1 from amount remaining
	beqz	$a2	count_increase_end # if no left, end
	j count_increases_loop		# loop
	
count_increase_end:
	jr	$ra

# part 2
count_windowed_increases:
	la 	$a0	number_list	# location of numbers in a0
	lw	$t2	($a0)		# load t0-2 with numbers on index 0-2
	addi 	$a0	$a0	4
	lw 	$t1	($a0)
	addi 	$a0	$a0	4
	lw 	$t0	($a0)
	addi 	$a0	$a0	4
	
	add	$a1	$t0	$t1
	add	$a1	$a1	$t2	# a1 to sum of numbers in 0-2
	lw 	$a2	amount_of_numbers	# load a2
	addi 	$a2	$a2	-3	# 3 numbers already considered
	li	$v0	0		# result = 0

# a0 = location of next new value
# a1 = previous sliding window total
# a2 = amount of numbers left
# t0 = value 
# t1 = value -1
# t2 = value -2
# t3 = current sliding window sum
# v0 = result: amount of increases	
count_windowed_increases_loop:
	move 	$t2	$t1
	move	$t1	$t0
	lw	$t0	($a0)		# load current value in t0
	add	$t3	$t0	$t1
	add	$t3	$t3	$t2	# current sliding window sum
	ble	$t3	$a1	count_windowed_increases_no # if no increase jump forward
	addi	$v0	$v0	1	# add 1 to amount of increases
count_windowed_increases_no:
	move	$a1	$t3		# previous value = current value
	addi	$a0	$a0	4	# next value location
	addi 	$a2	$a2	-1	# subtract 1 from amount remaining
	beqz	$a2	count_windowed_increases_end # if no left, end
	j 	count_windowed_increases_loop	# loop

count_windowed_increases_end:
	jr	$ra

# a0 = result
print_result:
	li	$v0	1	# syscall print integer
	syscall
	li 	$v0	11
	li	$a0	10  # syscall print end of line
	syscall
	jr 	$ra
	
