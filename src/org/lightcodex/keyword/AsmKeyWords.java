package org.lightcodex.keyword;

import org.llogger.LLogger;
import org.lxml.node.NodeList;

public class AsmKeyWords extends KeyWordGetter {

	public AsmKeyWords() {}
	
	private static final String[] baseInstrs = {
		"aaa", "aad", "aam", "aas", "adc",
		"adcx", "add", "adox", "and", "andn",
		"bextr", "blcfill", "blci", "blcic", 
		"blcmsk", "blcs", "blsfill", "blsi",
		"blsic", "blsmsk", "blsr", "bound", 
		"bsf", "bswap", "bt", "btc", "btr", 
		"bts", "bzhi", "call", "cbw", "cwde", 
		"cdqe", "cwd", "cdq", "cqo", "clc", 
		"cld", "clflush", "clflushopt", "clzero",
		"cmc", "cmovo", "cmovno", "cmovb",
		"cmovc", "cmovnae", "cmovnb", "cmovnc",
		"cmovae", "cmovz", "cmove", "cmovnz",
		"cmovne", "cmovbe", "cmovna", "cmovnbe",
		"cmova", "cmovs", "cmovns", "cmovp",
		"cmovpe", "cmovnp", "cmovpo", "cmovl",
		"cmovnge", "cmovnl", "cmovge", "cmovle",
		"cmovng", "cmovnle", "cmovg", "cmp",
		"cmps", "cmpsb", "cmpsw", "cmpsd", "cmpsq",
		"cmpxchg", "cmpxchg8b", "cmpxchg16b", "cpuid",
		"crc32", "daa", "das", "dec", "div", "enter",
		"idiv", "imul", "in", "inc", "incs",
		"incsb", "incsw", "incsd", "int", "into", 
		"jo", "jno", "jb", "jc", "jnae", "jnb", "jne",
		"jnc", "jae", "jz", "je", "jnz", "jbe", "jna",
		"jnbe", "ja", "js", "jns", "jp", "jpe", "jnp",
		"jpo", "jl", "jnge", "jnl", "jge", "jle", "jng",
		"jnle", "jg", "jcxz", "jecxz", "jrcxz", "jmp",
		"lahf", "lds", "les", "lfe", "lgs", "lss",
		"lea", "leave", "lfence", "llwpcb", "lods",
		"lodsb", "lodsw", "lodsd", "lodsq", "loop",
		"loope", "loopne", "loopnz", "loopz", "lwpins",
		"lwpval", "lzcnt", "mfence", "monitorx", "mov",
		"movbe", "movd", "movmskpd", "movmskps", "movnti",
		"movs", "movsb", "movsw", "movsd", "movsq",
		"movsx", "movsxd", "movzx", "mul", "mulx", "mwaitx",
		"neg", "nop", "not", "or", "out", "outs", "outsb", 
		"outsw", "outsd", "pause", "pdep", "pext", "pop",
		"popa", "popad", "popcnt", "popf", "popfd", "popfq",
		"prefetch", "prefetchw", "prefetchnta", "prefetcht0", 
		"prefetcht1", "prefetcht2", "push", "pusha", "pushad",
		"pushf", "pushfd", "pushfq", "rcl", "rcr", "rdfsbase",
		"rdgsbase", "rdrand", "rdseed", "retf", "ret", "rol",
		"ror", "rorx", "sahf", "sal", "shl", "sar", "sarx", 
		"sbb", "scas", "scasb", "scasw", "scasd", "scasq",
		"seto", "setno", "setb", "setc", "setnae", "setnb",
		"setnc", "setae", "setz", "sete", "setne", "setnz",
		"setbe", "setna", "setnbe", "seta", "sets", "setns",
		"setp", "setpe", "setnp", "setpo", "setl", "setnge",
		"setnl", "setge", "setle", "setng", "setnle", "setg",
		"sfence", "shl", "shld", "shlx", "shr", "shrd", "shrx",
		"slwpcb", "stc", "std", "stos", "stpsb", "stosw", "stosd",
		"stosq", "sub", "t1mskc", "test", "tzcnt", "tzmsk",
		"ud0", "ud1", "ud2", "wrfsbase", "wrgsbase", "xadd",
		"xchg", "xlat", "xlatb", "xor"
	};
	
	private static final String[] systemInstrs = {
		"arpl", "clac", "cli", "clts", "hlt", "invd", "invlpg",
		"invlpga", "iret", "iretd", "iretq", "lar", "lgdt", 
		"lidt", "lldt", "lmsw", "lsl", "ltr", "monitor", "mwait",
		"rdmsr", "rdpmc", "rdtsc", "rdtscp", "rsm", "sgdt", "sidt",
		"skinit", "sldt", "smsw", "stac", "sti", "stgi", "str", "swapgs",
		"syscall", "sysenter", "sysexit", "sysret", "verr", "verw", "vmload",
		"vmmcall", "vmrun", "vmsave", "wbinvd", "wrmsr"
	};
	
	private static final String[] mediaInstrs = {
		"vmovq", "vmovd", "roundsd"
	};
	
	private static final String[] directives = {
		"org", "default", "section", "rel", ".text", ".data", ".rdata", 
		"bits", "absolute", "segment",
		"extern", "global", "common", "cpu", "float", "warning", "file", "instrset",
		"format"
	};
	
	private static final String[] pseudoInstrs = {
		"times", "db", "dw", "dd", "dq", "dt", "resb", "resw", "resd", "resq",
		"rest", "incbin", "equ", "seg", "wrt"
	};
	
	private static final String[] registers1 = {
		"ah", "al", "bh", "bl", "ch", "cl", "dh", "dl",
		"ax", "bx", "cx", "dx", "si", "bp", "di", "sp",
		"eax", "ebx", "ecx", "edx", "esi", "edi", "ebp", "esp",
		"rax", "rbx", "rcx", "rdx", "rsi", "rdi", "rbp", "rsp",
		"r8", "r9", "r10", "r11", "r12", "r13", "r14", "r15",
		"eip", "rip", "ip", "cs", "ds", "es", "ss", "fs", "gs"	
	};
	
	private static final String[] registers2 = {
		"ymm0", "ymm1", "ymm2", "ymm3", "ymm4", "ymm5", "ymm6", "ymm7",
		"ymm8", "ymm9", "ymm10", "ymm11", "ymm12", "ymm13", "ymm14", "ymm15",
		"xmm0", "xmm1", "xmm2", "xmm3", "xmm4", "xmm5", "xmm6", "xmm7",
		"xmm8", "xmm9", "xmm10", "xmm11", "xmm12", "xmm13", "xmm14", "xmm15",
		"mmx0", "mmx1", "mmx2", "mmx3", "mmx4", "mmx5", "mmx6", "mmx7",
		"fpr0", "fpr1", "fpr2", "fpr3", "fpr4", "fpr5", "fpr6", "fpr7"
	};
	
	static {
		try {
			initKeyWords();
		} catch (Exception e) {
			LLogger.getInstance().
				logWarning("Key words file read failed.");
				baseInstrList   = baseInstrs;
				systemInstrList = systemInstrs;
				mediaInstrList  = mediaInstrs;
				directiveList   = directives;
				pseudoInstrList = pseudoInstrs;
				register1List   = registers1;
				register2List   = registers2;
		}
	}
	
	public static String[] baseInstrList;
	public static String[] systemInstrList;
	public static String[] mediaInstrList;
	public static String[] directiveList;
	public static String[] pseudoInstrList;
	public static String[] register1List;
	public static String[] register2List;
			
	private static void initKeyWords() {
		NodeList keyWords = root.getNodeList().getNodeByTag("Assembler").getNodeList(); 
		baseInstrList   = keyWords.getNodeByTag("base-instr").getContent().split(":");
		systemInstrList = keyWords.getNodeByTag("sys-instr").getContent().split(":");
		mediaInstrList  = keyWords.getNodeByTag("media-instr").getContent().split(":");
		directiveList   = keyWords.getNodeByTag("directive").getContent().split(":");
		pseudoInstrList = keyWords.getNodeByTag("pseudo-instr").getContent().split(":");
		register1List = keyWords.getNodeByTag("registers1").getContent().split(":");
		register2List = keyWords.getNodeByTag("registers2").getContent().split(":");
	}
}
