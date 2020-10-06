package org.lightcodex.keyword;

import org.llogger.LLogger;
import org.lxml.node.NodeList;

public class CSSKeyWords extends KeyWordGetter {

	public CSSKeyWords() {}
	
	private static String[] keyWords1 = {
		"display", "margin", "padding", "background-color",
		"position", "visibility", "text-align", "color",
		"text-decoration", "font-family", "min-width", "z-index",
		"hover", "left", "top", "overflow", "width", "height",
		"border-top", "border-left", "font-size",
		"margin-left", "font-style", "border-bottom", "border-right",
		"font", "font-weight", "padding-left", "padding-right", 
		"padding-top", "padding-bottom", "margin-right", 
		"margin-top", "margin-bottom", "text-shadow", 
		"border", "line-height", "direction", "overflow-x",
		"overflow-y", "word-wrap", "min-height", "text-wrap",
		"white-space", "text-indent", "content", "box-shadow",
		"user-select", "border-radius", "background-image",
		"line-style", "vertical-align", "border-collapse",
		"background", "border-spacing", "background-repeat",
		"border-top-right-radius", "border-top-left-radius",
		"border-bottom-right-radius", "border-bottom-left-radius",
		"float", "border-top-width", "outline",
		"cursor", "background-position", "caption-side",
		"list-stte-type", "border-color", "background-position-x",
		"max-width", "opacity", "border-top-color",
		"border-width", "right", "bottom", "border-bottom-color",
		"border-left-color", "border-right-color", 
		"page-break-after", "page-break-inside",
 		"webkit-transition", "moz-transition", 
		"ms-transition", "o-transition", "transition",
		"moz-column-gap", "webkit-column-gap",
		"moz-column-count", "webkit-column-count"
		
		,"active" , "after" , "before" , "first" , "first-child" , "first-letter" , "first-line" , 
		"focus" , "hover" , "lang" , "left" , "link" , "right" , "visited" , "charset" , 
		"font-face" , "import" , "media" , "page" , "above" , "ActiveBorder" , 
		"ActiveCaption" , "always" , "AppWorkspace" , "aqua" , "armenian" , "attr" , "auto" , 
		"avoid" , "azimuth" , "background" , "background-attachment" , "background-color" , 
		"background-image" , "background-position" , "background-repeat" , "baseline" , 
		"behind" , "below" , "bidi-override" , "black" , "blink" , "blue" , "bold" , 
		"bolder" , "border" , "border-bottom" , "border-bottom-color" , "border-bottom-style" , 
		"border-bottom-width" , "border-collapse" , "border-color" , "border-left" , 
		"border-left-color" , "border-left-style" , "border-left-width" , "border-right" , 
		"border-right-color" , "border-right-style" , "border-right-width" , "border-spacing" , 
		"border-style" , "border-top" , "border-top-color" , "border-top-style" , "border-top-width" , 
		"border-width" , "both" , "bottom" , "ButtonFace" , "ButtonHighlight" , 
		"ButtonShadow" , "ButtonText" , "capitalize" , "caption" , "caption-side" , "CaptionText" , 
		"center" , "center-left" , "center-right" , "circle" , "cjk-ideographic" , "clear" , 
		"clip" , "close-quote" , "code" , "collapse" , "color" , "compact" , "condensed" , "content" , 
		"continuous" , "counter-increment" , "counter-reset" , "crop" , "cros" , "crosshair" , 
		"cue" , "cue-after" , "cue-before" , "cursor" , "decimal" , "decimal-leading-zero" , 
		"default" , "deg" , "digits" , "direction" , "disc" , "display" , "e-resize" , "elevation" , 
		"em" , "embed" , "empty-cells" , "ex" , "expanded" , "extra-condensed" , "extra-expanded" , 
		"far-left" , "far-right" , "fast" , "faster" , "fixed" , "float" , "font" , "font-family" , 
		"font-size" , "font-size-adjust" , "font-stretch" , "font-style" , "font-variant" , 
		"font-weight" , "fuchsia" , "georgian" , "grad" , "GrayText" , "green" , "hebrew" , 
		"height" , "help" , "hidden" , "hide" , "high" , "higher" , "Highlight" , "HighlightText" , 
		"hiragana" , "hiragana-iroha" , "hz" , "icon" , "InactiveBorder" , "InactiveCaption" , 
		"InactiveCaptionText" , "InfoBackground" , "InfoText" , "inherit" , "inline-table" , 
		"inside" , "italic" , "justify" , "katakana" , "katakana-iroha" , "khz" , "landscape" , 
		"left" , "left-side" , "leftwards" , "letter-spacing" , "level" , "lighter" , "lime" , 
		"line-height" , "line-through" , "list-item" , "list-style" , "list-style-image" , 
		"list-style-position" , "list-style-type" , "loud" , "low" , "lower" , "lower-alpha" , 
		"lower-greek" , "lower-latin" , "lower-roman" , "lowercase" , "ltr" , "margin" , "margin-bottom" , 
		"margin-left" , "margin-right" , "margin-top" , "marker" , "marker-offset" , 
		"marks" , "maroon" , "max-height" , "max-width" , "medium" , "menu" , "MenuText" , "message-box" , 
		"middle" , "min-height" , "min-width" , "mix" , "move" , "ms" , "n-resize" , "narrower" , 
		"navy" , "ne-resize" , "no-close-quote" , "no-open-quote" , "no-repeat" , "none" , 
		"normal" , "nowrap" , "nw-resize" , "oblique" , "olive" , "once" , "open-quote" , "orphans" , 
		"outline" , "outline-color" , "outline-style" , "outline-width" , "outside" , "overflow" , 
		"overline" , "padding" , "padding-bottom" , "padding-left" , "padding-right" , "padding-top" , 
		"page" , "page-break-after" , "page-break-before" , "page-break-inside" , 
		"pause" , "pause-after" , "pause-before" , "pitch" , "pitch-range" , "play-during" , 
		"pointer" , "portrait" , "position" , "pre" , "pt" , "purple" , "px" , "quotes" , "rad" , 
		"relative" , "repeat" , "repeat-x" , "repeat-y" , "richness" , "right" , "right-side" , 
		"rightwards" , "rtl" , "run-in" , "s-resize" , "scroll" , "Scrollbar" , "se-resize" , 
		"semi-condensed" , "semi-expanded" , "separate" , "show" , "silent" , "silver" , "size" , 
		"slow" , "slower" , "small-caps" , "small-caption" , "soft" , "speak" , "speak-header" , 
		"speak-numeral" , "speak-ponctuation" , "speech-rate" , "spell-out" , "square" , 
		"static" , "status-bar" , "stress" , "sub" , "super" , "sw-resize" , "table" , "table-caption" , 
		"table-cell" , "table-column" , "table-column-group" , "table-footer-group" , "table-header-group" , 
		"table-layout" , "table-row" , "table-row-group" , 
		"teal" , "text" , "text-align" , "text-bottom" , "text-decoration" , "text-indent" , 
		"text-shadow" , "text-top" , "text-transform" , "ThreeDDarkShadow" , "ThreeDFace" , 
		"ThreeDHighlight" , "ThreeDLightShadow" , "ThreeDShadow" , "top" , "transparent" , 
		"ultra-condensed" , "ultra-expanded" , "underline" , "unicode-bidi" , "upper-alpha" , 
		"upper-latin" , "upper-roman" , "uppercase" , "vertical-align" , "visibility" , 
		"visible" , "voice-family" , "volume" , "w-resize" , "wait" , "white" , "white-space" , 
		"wider" , "widows" , "width" , "Window" , "WindowFrame" , "WindowText" , "word-spacing" , 
		"x-fast" , "x-high" , "x-loud" , "x-low" , "x-slow" , "x-soft" , "yellow" , "z-index" ,
	};
		
	private static String[] keyWords2 = {
		"red", "inline", "inline-block", "gray", "block",
		"absolute", "relative", "gold", "black", "orange",
		"center", "none", "visible", "gray", "italic", "bold",
		"auto", "solid", "green", "middle",
		"white", "lightgrey", "Microsoft", "YaHei", "fixed",
		"static", "normal",  "linear", "double", "cyan", 
		"underline", "hidden", "ltr", "break-word",
		"unrestricted", "moz-pre-wrap", "pre-wrap",
		"o-pre-wrap", "pre", "rgba", "nowrap", "collapse",
		"url", "repeat-x", "table", "bottom", "right", 
		"pointer", "repeat-y", "top", "left", "no-repeat",
		"separate", "medium", "outside", "default", "smaller",
		"grey", "transparent", "avoid", "inherit", "rtl"
	};
	
	public static String[] keyWord1List;
	public static String[] keyWord2List;
	
	
	static {
		try {
			initKeyWords();
		} catch (Exception e) {
			LLogger.getInstance().
				logWarning("Key words file read failed.");
			keyWord1List = keyWords1;
			keyWord2List = keyWords2;
		}
	}
	
	private static void initKeyWords() {
		NodeList keyWords = root.getNodeList().getNodeByTag("CSS").getNodeList(); 
		keyWord1List = keyWords.getNodeByTag("keyword1").getContent().split(":");
		keyWord2List = keyWords.getNodeByTag("keyword2").getContent().split(":");
	}
}
