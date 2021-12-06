.data 
	input_file:		.asciiz "C:\\Users\\ToonDeburchgrave\\Dev\\aedvent-code-2021\\day 06\\Toon - MIPS\\input"
	.align 	2
	buffer:			.space 	600
	fishes_alive: 		.space	72

.eqv	amount_numbers	300
.eqv	amount_of_days	256
.text

main:
	jal	read_file
	jal	input_to_fishes_alive
	jal	loop_days
	jal	sum
	move	$s0	$t0
	move	$s1	$t1
	move	$a0	$s1
	li	$v0	35	# load print integer syscall
	syscall
	move	$a0	$s0
	li	$v0	35
	syscall
	li	$v0	10	# load terminate syscall
	syscall
	
loop_days:
	li	$s0	amount_of_days
	move	$s1	$ra	# save return address in s1
loop:
	jal	day		# do one day
	subi	$s0	$s0	1	# subtract 1 from remaining days
	beqz	$s0	end_loop	# end loop if 0 days left
	j	loop
	
end_loop:
	move	$ra	$s1	# restore return address
	jr	$ra		# return to main
	
sum:
	move	$s1	$ra
	la	$a0	fishes_alive
	li	$t0	0
	li	$t1	0
	
	ld	$t2	0($a0)
	jal	add64
	move	$t0	$t4
	move	$t1	$t5
	ld	$t2	8($a0)
	jal	add64
	move	$t0	$t4
	move	$t1	$t5
	ld	$t2	16($a0)
	jal	add64
	move	$t0	$t4
	move	$t1	$t5
	ld	$t2	24($a0)
	jal	add64
	move	$t0	$t4
	move	$t1	$t5
	ld	$t2	32($a0)
	jal	add64
	move	$t0	$t4
	move	$t1	$t5
	ld	$t2	40($a0)
	jal	add64
	move	$t0	$t4
	move	$t1	$t5
	ld	$t2	48($a0)
	jal	add64
	move	$t0	$t4
	move	$t1	$t5
	ld	$t2	56($a0)
	jal	add64
	move	$t0	$t4
	move	$t1	$t5
	ld	$t2	64($a0)
	jal	add64
	move	$t0	$t4
	move	$t1	$t5
	
	move 	$ra	$s1
	jr	$ra	
	

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


# turn ascii into integers and add them to the fishes alive list
# assume the initial list does not contain more than 32-bit interger max value
input_to_fishes_alive:
	la	$a0	buffer		# a0 = location of next input number
	la	$a1	fishes_alive	# a1 = location of fishes alive list
	li	$a2	amount_numbers	# a2 = amount of numbers in the buffer
	
input_to_fishes_alive_loop:
	lb	$t0	($a0)		# t0 = next character
	andi	$t0	$t0	0x0F	# turn ascii into number
	mul	$t0	$t0	8	# number * 8 (for memory location)
	add	$t0	$a1	$t0	# location in fish list = start fish list + (number * 4)
	lw	$t1	($t0)		# t1 = content of fish_list[number]
	addi	$t1	$t1	1	# add one
	sw	$t1	($t0)		# fish_list[number] = t1 (=fish_list[number]+1)
	
	addi	$a0	$a0	2	# go to next character, skipping the ,
	subi	$a2	$a2	1	# -1 on remaining numbers
	
	beqz	$a2	input_to_fishes_alive_end
	j	input_to_fishes_alive_loop
	
input_to_fishes_alive_end:
	jr	$ra

# t0 = lowest order 32 bit
# t1 = highest order 64 bit		
day:
	move	$s2	$ra
	la	$a0	fishes_alive	# start of list
	
	ld	$t0	($a0)		# get first element
	
	ld	$t2	8($a0)		# get second element
	sd	$t2	0($a0)		# store in first element
	ld	$t2	16($a0)		# shift others
	sd	$t2	8($a0)
	ld	$t2	24($a0)
	sd	$t2	16($a0)
	ld	$t2	32($a0)
	sd	$t2	24($a0)
	ld	$t2	40($a0)
	sd	$t2	32($a0)
	ld	$t2	48($a0)
	sd	$t2	40($a0)
	ld	$t2	56($a0)
	jal	add64			# add fishes from day 0 (result t4-t5)
	move	$t2	$t4
	move	$t3	$t5
	sd	$t2	48($a0)
	ld	$t2	64($a0)
	sd	$t2	56($a0)
	sd	$t0	64($a0)		# new born fishes
	move 	$ra	$s2
	jr 	$ra

# t0-t1 first number 
# t2-t3	second number
# t4-t5
add64:	
	addu  $t4, $t0, $t2    # add least significant word
	sltu  $t5, $t4, $t2    # set carry-in bit 
	addu  $t5, $t5, $t1    # add in first most significant word
	addu  $t5, $t5, $t3    # add in second most significant word
	jr    $ra
	