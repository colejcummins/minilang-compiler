target triple="x86_64-apple-macosx10.15.0"

@y = common global i64 0, align 4

define void @foo(i64 %x)
{
LU2:
  br label %LU3
LU3:
  call i64 (i8*, ...) @printf(i8* getelementptr inbounds ([5 x i8], [5 x i8]* @.print, i32 0, i32 0), i64 96)
  call i64 (i8*, ...) @printf(i8* getelementptr inbounds ([5 x i8], [5 x i8]* @.print, i32 0, i32 0), i64 4)
  call i64 (i8*, ...) @printf(i8* getelementptr inbounds ([5 x i8], [5 x i8]* @.print, i32 0, i32 0), i64 7)
  call i64 (i8*, ...) @printf(i8* getelementptr inbounds ([5 x i8], [5 x i8]* @.print, i32 0, i32 0), i64 28)
  call i64 (i8*, ...) @printf(i8* getelementptr inbounds ([5 x i8], [5 x i8]* @.print, i32 0, i32 0), i64 4)
  call i64 (i8*, ...) @scanf(i8* getelementptr inbounds ([4 x i8], [4 x i8]* @.read, i32 0, i32 0), i64* @.read_scratch)
  %u8 = load i64, i64* @.read_scratch
  %u9 = add i64 %u8, 1
  call i64 (i8*, ...) @printf(i8* getelementptr inbounds ([5 x i8], [5 x i8]* @.print, i32 0, i32 0), i64 %u9)
  br label %LU4
LU4:
  ret void
}


define i64 @bar(i64 %x, i64 %y)
{
LU11:
  br label %LU12
LU12:
  %u10 = icmp sgt i64 %x, %y
  %u11 = zext i1 %u10 to i64
  %u12 = trunc i64 %u11 to i1
  br i1 %u12, label %LU14, label %LU15
LU14:
  br label %LU13
LU13:
  br label %LU16
LU16:
  ret i64 5
LU15:
  br label %LU13
}


define i64 @baz(i64 %x, i64 %y)
{
LU21:
  br label %LU22
LU22:
  %u19 = icmp sgt i64 %x, %y
  %u20 = zext i1 %u19 to i64
  %u21 = trunc i64 %u20 to i1
  br i1 %u21, label %LU24, label %LU25
LU24:
  br label %LU23
LU23:
  br label %LU26
LU26:
  br i1 1, label %LU28, label %LU29
LU28:
  call i64 (i8*, ...) @printf(i8* getelementptr inbounds ([5 x i8], [5 x i8]* @.println, i32 0, i32 0), i64 1)
  br label %LU27
LU27:
  br label %LU30
LU30:
  br i1 1, label %LU32, label %LU33
LU32:
  call i64 (i8*, ...) @printf(i8* getelementptr inbounds ([5 x i8], [5 x i8]* @.println, i32 0, i32 0), i64 3)
  br label %LU31
LU31:
  br label %LU34
LU34:
  ret i64 0
LU33:
  call i64 (i8*, ...) @printf(i8* getelementptr inbounds ([5 x i8], [5 x i8]* @.println, i32 0, i32 0), i64 4)
  br label %LU31
LU29:
  call i64 (i8*, ...) @printf(i8* getelementptr inbounds ([5 x i8], [5 x i8]* @.println, i32 0, i32 0), i64 2)
  br label %LU27
LU25:
  br label %LU23
}


define void @fbool(i64 %b)
{
LU41:
  br label %LU42
LU42:
  ret void
}


define i64 @quux(i64 %x, i64 %y)
{
LU51:
  br label %LU52
LU52:
  %u41 = icmp sgt i64 %x, %y
  %u42 = zext i1 %u41 to i64
  %u43 = trunc i64 %u42 to i1
  br i1 %u43, label %LU54, label %LU55
LU54:
  br label %LU53
LU53:
  br label %LU56
LU56:
  call void @fbool(i64 0)
  call void @fbool(i64 1)
  call void @fbool(i64 1)
  call void @fbool(i64 1)
  call void @fbool(i64 0)
  call void @fbool(i64 0)
  call void @fbool(i64 1)
  call void @fbool(i64 1)
  br label %LU57
LU57:
  ret i64 1
LU55:
  br label %LU53
}


define i64 @quux2(i64 %x, i64 %y)
{
LU61:
  br label %LU62
LU62:
  %u66 = icmp sgt i64 %x, %y
  %u67 = zext i1 %u66 to i64
  %u68 = trunc i64 %u67 to i1
  br i1 %u68, label %LU64, label %LU65
LU64:
  br label %LU63
LU63:
  br label %LU66
LU66:
  call void @fbool(i64 0)
  call void @fbool(i64 1)
  call void @fbool(i64 1)
  call void @fbool(i64 1)
  call void @fbool(i64 0)
  call void @fbool(i64 0)
  call void @fbool(i64 1)
  call void @fbool(i64 1)
  br label %LU67
LU67:
  ret i64 1
LU65:
  br label %LU63
}


define i64 @flrgrl(i64 %x, i64 %y)
{
LU71:
  br label %LU72
LU72:
  %u92 = icmp sgt i64 %x, %y
  %u93 = zext i1 %u92 to i64
  %u94 = trunc i64 %u93 to i1
  br i1 %u94, label %LU74, label %LU75
LU74:
  br label %LU73
LU73:
  br label %LU76
LU76:
  br label %LU77
LU77:
  ret i64 4
LU75:
  br label %LU73
}


define i64 @blergh()
{
LU81:
  br label %LU82
LU82:
  br i1 1, label %LU84, label %LU83
LU84:
  br label %LU83
LU83:
  br label %LU85
LU85:
  ret i64 3
}


define i64 @main()
{
LU91:
  br label %LU92
LU92:
  ret i64 0
}


declare i8* @malloc(i64)
declare void @free(i8*)
declare i64 @printf(i8*, ...)
declare i64 @scanf(i8*, ...)
@.println = private unnamed_addr constant [5 x i8] c"%ld\0A\00", align 1
@.print = private unnamed_addr constant [5 x i8] c"%ld \00", align 1
@.read = private unnamed_addr constant [4 x i8] c"%ld\00", align 1
@.read_scratch = common global i64 0, align 4

