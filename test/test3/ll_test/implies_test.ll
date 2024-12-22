@.strP = private unnamed_addr constant [4 x i8] c"%d\0A\00", align 1

define void @println(i32 %x) {
    %1 = alloca i32, align 4
    store i32 %x, i32* %1, align 4
    %2 = load i32, i32* %1, align 4
    %3 = call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([4 x i8], [4 x i8]* @.strP, i32 0, i32 0), i32 %2)
    ret void
}

@.str = private unnamed_addr constant [3 x i8] c"%d\00", align 1

define i32 @readInt() #0 {
    %1 = alloca i32, align 4
    %2 = call i32 (i8*, ...) @scanf(i8* getelementptr inbounds ([3 x i8], [3 x i8]* @.str, i32 0, i32 0), i32* %1)
    %3 = load i32, i32* %1, align 4
    ret i32 %3
}

declare i32 @scanf(i8*, ...) #1
declare i32 @printf(i8*, ...)

define i32 @main() {
    entry:
        %a = alloca i32
        %0 = call i32 @readInt()
        store i32 %0, i32* %a
        %b = alloca i32
        %1 = call i32 @readInt()
        store i32 %1, i32* %b
        br label %label0
    label0:
        %2 = add i32 0, 2
        %3 = load i32, i32* %b
        %4 = icmp slt i32 %2, %3
        %5 = add i32 0, 2
        %6 = load i32, i32* %a
        %7 = icmp slt i32 %5, %6
        %8 = xor i1 %4, true
        %9 = or i1 %8, %7
        br i1 %9, label %label1, label %label2
    label1:
        %10 = load i32, i32* %a
        call void @println(i32 %10)
        %11 = load i32, i32* %b
        call void @println(i32 %11)
        %12 = call i32 @readInt()
        store i32 %12, i32* %a
        %13 = call i32 @readInt()
        store i32 %13, i32* %b
        br label %label0
    label2:
        %14 = load i32, i32* %a
        call void @println(i32 %14)
        ret i32 0
}