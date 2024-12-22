
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
        %1 = load i32, i32* %a
        %2 = add i32 0, 5
        %3 = icmp slt i32 %1, %2
        br i1 %3, label %label0, label %label1
    label0:
        %4 = add i32 0, 0
        store i32 %4, i32* %a
        %5 = load i32, i32* %a
        call void @println(i32 %5)
        br label %label2
    label1:
        %6 = add i32 0, 10
        store i32 %6, i32* %a
        %7 = load i32, i32* %a
        call void @println(i32 %7)
        br label %label2
    label2:
        ret i32 0
}

