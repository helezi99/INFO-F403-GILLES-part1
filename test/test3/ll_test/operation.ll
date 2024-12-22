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
        %d = alloca i32
        %0 = add i32 0, 1
        %1 = add i32 0, 1
        %2 = sub i32 0, %1
        %3 = add i32 0, 2
        %4 = mul i32 %2, %3
        %5 = add i32 %0, %4
        %6 = add i32 0, 25
        %7 = add i32 0, 7
        %8 = sub i32 0, %7
        %9 = add i32 0, 12
        %10 = add i32 %8, %9
        %11 = sdiv i32 %6, %10
        %12 = sub i32 %5, %11
        %13 = add i32 0, 1
        %14 = add i32 0, 4
        %15 = add i32 0, 2
        %16 = mul i32 %14, %15
        %17 = sub i32 %13, %16
        %18 = sub i32 %12, %17
        store i32 %18, i32* %d
        %19 = load i32, i32* %d
        call void @println(i32 %19)
        ret i32 0
}