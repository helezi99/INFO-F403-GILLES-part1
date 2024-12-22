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
        %i = alloca i32
        %0 = add i32 0, 0
        store i32 %0, i32* %i
        br label %label0
    label0:
        %1 = load i32, i32* %i
        %2 = add i32 0, 10
        %3 = icmp sle i32 %1, %2
        br i1 %3, label %label1, label %label2
    label1:
        %4 = load i32, i32* %i
        call void @println(i32 %4)
        %5 = load i32, i32* %i
        %6 = add i32 0, 1
        %7 = add i32 %5, %6
        store i32 %7, i32* %i
        br label %label0
    label2:
        ret i32 0
}