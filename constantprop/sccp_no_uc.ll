target triple="i686"
@y = common global i32 0, align 8

define void @foo(i32 %x)
{
LU1:
	%u0 = load i32* @y
	%u1 = add i32 %u0, 1
	%u2 = add i32 %x, 1
	%u3 = add i32 3, 4
	%u4 = add i32 4, 7
	%u5 = mul i32 4, 7
	%u6 = sub i32 99, 3
	call i32 (i8*, ...)* @printf(i8* getelementptr inbounds ([5 x i8]* @.print, i32 0, i32 0), i32 96)
	call i32 (i8*, ...)* @printf(i8* getelementptr inbounds ([5 x i8]* @.print, i32 0, i32 0), i32 4)
	call i32 (i8*, ...)* @printf(i8* getelementptr inbounds ([5 x i8]* @.print, i32 0, i32 0), i32 7)
	call i32 (i8*, ...)* @printf(i8* getelementptr inbounds ([5 x i8]* @.print, i32 0, i32 0), i32 28)
	%u7 = sdiv i32 4, 0
	call i32 (i8*, ...)* @printf(i8* getelementptr inbounds ([5 x i8]* @.print, i32 0, i32 0), i32 %u7)
	call i32 (i8*, ...)* @scanf(i8* getelementptr inbounds ([4 x i8]* @.read, i32 0, i32 0), i32* @.read_scratch)
	%u8 = load i32* @.read_scratch
	%u9 = add i32 %u8, 1
	call i32 (i8*, ...)* @printf(i8* getelementptr inbounds ([5 x i8]* @.print, i32 0, i32 0), i32 %u9)
	br label %LU0
LU0:
	ret void
}

define i32 @bar(i32 %x, i32 %y)
{
LU3:
	%u10 = icmp sgt i32 %x, %y
	br i1 %u10, label %LU4, label %LU5
LU4:
	br label %LU6
LU5:
	br label %LU6
LU6:
	%u11 = add i32 2, 3
	br label %LU2
LU2:
	ret i32 5
}

define i32 @baz(i32 %x, i32 %y)
{
LU8:
	%u12 = zext i1 true to i32
	%u13 = icmp sgt i32 %x, %y
	br i1 %u13, label %LU9, label %LU10
LU9:
	%u14 = zext i1 false to i32
	br label %LU11
LU10:
	%u15 = zext i1 false to i32
	br label %LU11
LU11:
	%u16 = trunc i32 1 to i1
	%u17 = trunc i32 0 to i1
	%u18 = or i1 true, false
	br label %LU12
LU12:
	call i32 (i8*, ...)* @printf(i8* getelementptr inbounds ([5 x i8]* @.println, i32 0, i32 0), i32 1)
	br label %LU14
LU14:
	%u19 = trunc i32 1 to i1
	%u20 = xor i1 true, true
	br label %LU16
LU16:
	call i32 (i8*, ...)* @printf(i8* getelementptr inbounds ([5 x i8]* @.println, i32 0, i32 0), i32 4)
	br label %LU17
LU17:
	%u21 = trunc i32 1 to i1
	%u22 = trunc i32 0 to i1
	%u23 = and i1 true, false
	%u24 = zext i1 false to i32
	br label %LU7
LU7:
	ret i32 0
}

define void @fbool(i32 %b)
{
LU19:
	br label %LU18
LU18:
	ret void
}

define i32 @quux(i32 %x, i32 %y)
{
LU21:
	%u25 = icmp sgt i32 %x, %y
	br i1 %u25, label %LU22, label %LU23
LU22:
	br label %LU24
LU23:
	br label %LU24
LU24:
	%u26 = icmp eq i32 1, 2
	%u28 = zext i1 false to i32
	call void @fbool(i32 0)
	%u29 = icmp ne i32 1, 2
	%u31 = zext i1 true to i32
	call void @fbool(i32 1)
	%u32 = icmp slt i32 1, 2
	%u34 = zext i1 true to i32
	call void @fbool(i32 1)
	%u35 = icmp sle i32 1, 2
	%u37 = zext i1 true to i32
	call void @fbool(i32 1)
	%u38 = icmp sgt i32 1, 2
	%u40 = zext i1 false to i32
	call void @fbool(i32 0)
	%u41 = icmp sge i32 1, 2
	%u43 = zext i1 false to i32
	call void @fbool(i32 0)
	%u44 = icmp sge i32 2, 2
	%u46 = zext i1 true to i32
	call void @fbool(i32 1)
	%u47 = icmp sle i32 2, 2
	%u49 = zext i1 true to i32
	call void @fbool(i32 1)
	%u50 = zext i1 false to i32
	br label %LU20
LU20:
	ret i32 0
}

define i32 @quux2(i32 %x, i32 %y)
{
LU26:
	%u51 = icmp sgt i32 %x, %y
	br i1 %u51, label %LU27, label %LU28
LU27:
	%u52 = add i32 1, 1
	br label %LU29
LU28:
	br label %LU29
LU29:
	%u53 = icmp eq i32 1, 2
	%u55 = zext i1 false to i32
	call void @fbool(i32 0)
	%u56 = icmp ne i32 1, 2
	%u58 = zext i1 true to i32
	call void @fbool(i32 1)
	%u59 = icmp slt i32 1, 2
	%u61 = zext i1 true to i32
	call void @fbool(i32 1)
	%u62 = icmp sle i32 1, 2
	%u64 = zext i1 true to i32
	call void @fbool(i32 1)
	%u65 = icmp sgt i32 1, 2
	%u67 = zext i1 false to i32
	call void @fbool(i32 0)
	%u68 = icmp sge i32 1, 2
	%u70 = zext i1 false to i32
	call void @fbool(i32 0)
	%u71 = icmp sge i32 2, 2
	%u73 = zext i1 true to i32
	call void @fbool(i32 1)
	%u74 = icmp sle i32 2, 2
	%u76 = zext i1 true to i32
	call void @fbool(i32 1)
	%u77 = zext i1 false to i32
	br label %LU25
LU25:
	ret i32 0
}

define i32 @flrgrl(i32 %x, i32 %y)
{
LU31:
	%u78 = icmp sgt i32 %x, %y
	br i1 %u78, label %LU32, label %LU33
LU32:
	%u79 = add i32 1, 1
	br label %LU34
LU33:
	br label %LU34
LU34:
	%u80 = add i32 2, 2
	br label %LU30
LU30:
	ret i32 4
}

define i32 @blergh()
{
LU36:
	%u81 = icmp slt i32 1, 2
	br label %LU37
LU37:
	br label %LU39
LU39:
	br label %LU35
LU35:
	ret i32 3
}

define i32 @main()
{
LU41:
	br label %LU40
LU40:
	ret i32 0
}

declare i8* @malloc(i32)
declare void @free(i8*)
declare i32 @printf(i8*, ...)
declare i32 @scanf(i8*, ...)
@.println = private unnamed_addr constant [5 x i8] c"%ld\0A\00", align 1
@.print = private unnamed_addr constant [5 x i8] c"%ld \00", align 1
@.read = private unnamed_addr constant [4 x i8] c"%ld\00", align 1
@.read_scratch = common global i32 0, align 4