
/**
 * 这个包负责 LightCodeX 的语言管理功能.
 * 
 * 所谓语言管理, 就是在 LightCodeX 打开文件时, 程序能够根据后缀名确定使用哪种编辑器, 
 * 以及使用哪些自动单词完成数据文件. 比如, 如果当前打开的文件的后缀名是  cpp, 
 * 那么 LightCodeX 会将其视为 C/C++ 语言源代码,然后使用 CTextEditor,
 * 并且使用对应的自动完成单词数据文件(如果开启了自动完成单词功能).
 * <br/>
 * <br/>
 * 语言管理要实现的基本功能有:
 * <ol>
 * 	<li>添加语言</li>
 * 	<li>删除语言</li>
 *  <li>添加扩展名到语言</li>
 *  <li>删除语言的某个扩展名</li>
 * </ol>
 * 
 * 以上基本功能需要使用<b>语言管理对话框</b>完成. 语言管理对话框提供实现上述功能的用户接口, 
 * <b>LangManager</b> 实现数据存储支持. LangManager 使用 LXMLDocument 来维护数据.
 * <br/>
 * <br/>
 * 为了程序的健壮性, 在 LangManager 读取或解析 LXMLDocument 遇到错误时, 要提供默认设定. 
 * */

package org.lightcodex.lang;