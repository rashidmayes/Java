/*******************************************************************************
 * Copyright (c) 2009 Licensed under the Apache License, 
 * Version 2.0 (the "License"); you may not use this file except in compliance 
 * with the License. You may obtain a copy of the License at 
 * 
 * http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the 
 * License for the specific language governing permissions and limitations under
 * the License.
 * 
 * Contributors:
 * 
 * Astrient Foundation Inc. 
 * www.astrientfoundation.org
 * rashid@astrientfoundation.org
 * Rashid Mayes 2009
 *******************************************************************************/
package org.astrientfoundation.util;

import java.awt.Color;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class Colors
{
    public static final Color Snow                  = new Color(255,250,250);    //#FFFAFA
    public static final Color GhostWhite            = new Color(248,248,255);    //#F8F8FF
    public static final Color WhiteSmoke            = new Color(245,245,245);    //#F5F5F5
    public static final Color Gainsboro             = new Color(220,220,220);    //#DCDCDC
    public static final Color FloralWhite           = new Color(255,250,240);    //#FFFAF0
    public static final Color OldLace               = new Color(253,245,230);    //#FDF5E6
    public static final Color Linen                 = new Color(250,240,230);    //#FAF0E6
    public static final Color AntiqueWhite          = new Color(250,235,215);    //#FAEBD7
    public static final Color PapayaWhip            = new Color(255,239,213);    //#FFEFD5
    public static final Color BlanchedAlmond        = new Color(255,235,205);    //#FFEBCD
    public static final Color Bisque                = new Color(255,228,196);    //#FFE4C4
    public static final Color PeachPuff             = new Color(255,218,185);    //#FFDAB9
    public static final Color NavajoWhite           = new Color(255,222,173);    //#FFDEAD
    public static final Color Moccasin              = new Color(255,228,181);    //#FFE4B5
    public static final Color Cornsilk              = new Color(255,248,220);    //#FFF8DC
    public static final Color Ivory                 = new Color(255,255,240);    //#FFFFF0
    public static final Color LemonChiffon          = new Color(255,250,205);    //#FFFACD
    public static final Color Seashell              = new Color(255,245,238);    //#FFF5EE
    public static final Color Honeydew              = new Color(240,255,240);    //#F0FFF0
    public static final Color MintCream             = new Color(245,255,250);    //#F5FFFA
    public static final Color Azure                 = new Color(240,255,255);    //#F0FFFF
    public static final Color AliceBlue             = new Color(240,248,255);    //#F0F8FF
    public static final Color lavender              = new Color(230,230,250);    //#E6E6FA
    public static final Color LavenderBlush         = new Color(255,240,245);    //#FFF0F5
    public static final Color MistyRose             = new Color(255,228,225);    //#FFE4E1
    public static final Color White                 = new Color(255,255,255);    //#FFFFFF
    public static final Color Black                 = new Color(0,0,0);    //#000000
    public static final Color DarkSlateGray         = new Color(47,79,79);    //#2F4F4F
    public static final Color DimGrey               = new Color(105,105,105);    //#696969
    public static final Color SlateGrey             = new Color(112,128,144);    //#708090
    public static final Color LightSlateGray        = new Color(119,136,153);    //#778899
    public static final Color Grey                  = new Color(190,190,190);    //#BEBEBE
    public static final Color LightGray             = new Color(211,211,211);    //#D3D3D3
    public static final Color MidnightBlue          = new Color(25,25,112);    //#191970
    public static final Color NavyBlue              = new Color(0,0,128);    //#000080
    public static final Color CornflowerBlue        = new Color(100,149,237);    //#6495ED
    public static final Color DarkSlateBlue         = new Color(72,61,139);    //#483D8B
    public static final Color SlateBlue             = new Color(106,90,205);    //#6A5ACD
    public static final Color MediumSlateBlue       = new Color(123,104,238);    //#7B68EE
    public static final Color LightSlateBlue        = new Color(132,112,255);    //#8470FF
    public static final Color MediumBlue            = new Color(0,0,205);    //#0000CD
    public static final Color RoyalBlue             = new Color(65,105,225);    //#4169E1
    public static final Color Blue                  = new Color(0,0,255);    //#0000FF
    public static final Color DodgerBlue            = new Color(30,144,255);    //#1E90FF
    public static final Color DeepSkyBlue           = new Color(0,191,255);    //#00BFFF
    public static final Color SkyBlue               = new Color(135,206,235);    //#87CEEB
    public static final Color LightSkyBlue          = new Color(135,206,250);    //#87CEFA
    public static final Color SteelBlue             = new Color(70,130,180);    //#4682B4
    public static final Color LightSteelBlue        = new Color(176,196,222);    //#B0C4DE
    public static final Color LightBlue             = new Color(173,216,230);    //#ADD8E6
    public static final Color PowderBlue            = new Color(176,224,230);    //#B0E0E6
    public static final Color PaleTurquoise         = new Color(175,238,238);    //#AFEEEE
    public static final Color DarkTurquoise         = new Color(0,206,209);    //#00CED1
    public static final Color MediumTurquoise       = new Color(72,209,204);    //#48D1CC
    public static final Color Turquoise             = new Color(64,224,208);    //#40E0D0
    public static final Color Cyan                  = new Color(0,255,255);    //#00FFFF
    public static final Color LightCyan             = new Color(224,255,255);    //#E0FFFF
    public static final Color CadetBlue             = new Color(95,158,160);    //#5F9EA0
    public static final Color MediumAquamarine      = new Color(102,205,170);    //#66CDAA
    public static final Color Aquamarine            = new Color(127,255,212);    //#7FFFD4
    public static final Color DarkGreen             = new Color(0,100,0);    //#006400
    public static final Color DarkOliveGreen        = new Color(85,107,47);    //#556B2F
    public static final Color DarkSeaGreen          = new Color(143,188,143);    //#8FBC8F
    public static final Color SeaGreen              = new Color(46,139,87);    //#2E8B57
    public static final Color MediumSeaGreen        = new Color(60,179,113);    //#3CB371
    public static final Color LightSeaGreen         = new Color(32,178,170);    //#20B2AA
    public static final Color PaleGreen             = new Color(152,251,152);    //#98FB98
    public static final Color SpringGreen           = new Color(0,255,127);    //#00FF7F
    public static final Color LawnGreen             = new Color(124,252,0);    //#7CFC00
    public static final Color Green                 = new Color(0,255,0);    //#00FF00
    public static final Color Chartreuse            = new Color(127,255,0);    //#7FFF00
    public static final Color MedSpringGreen        = new Color(0,250,154);    //#00FA9A
    public static final Color GreenYellow           = new Color(173,255,47);    //#ADFF2F
    public static final Color LimeGreen             = new Color(50,205,50);    //#32CD32
    public static final Color YellowGreen           = new Color(154,205,50);    //#9ACD32
    public static final Color ForestGreen           = new Color(34,139,34);    //#228B22
    public static final Color OliveDrab             = new Color(107,142,35);    //#6B8E23
    public static final Color DarkKhaki             = new Color(189,183,107);    //#BDB76B
    public static final Color PaleGoldenrod         = new Color(238,232,170);    //#EEE8AA
    public static final Color LtGoldenrodYello      = new Color(250,250,210);    //#FAFAD2
    public static final Color LightYellow           = new Color(255,255,224);    //#FFFFE0
    public static final Color Yellow                = new Color(255,255,0);    //#FFFF00
    public static final Color Gold          = new Color(255,215,0);    //#FFD700
    public static final Color LightGoldenrod        = new Color(238,221,130);    //#EEDD82
    public static final Color goldenrod             = new Color(218,165,32);    //#DAA520
    public static final Color DarkGoldenrod         = new Color(184,134,11);    //#B8860B
    public static final Color RosyBrown             = new Color(188,143,143);    //#BC8F8F
    public static final Color IndianRed             = new Color(205,92,92);    //#CD5C5C
    public static final Color SaddleBrown           = new Color(139,69,19);    //#8B4513
    public static final Color Sienna                = new Color(160,82,45);    //#A0522D
    public static final Color Peru          = new Color(205,133,63);    //#CD853F
    public static final Color Burlywood             = new Color(222,184,135);    //#DEB887
    public static final Color Beige                 = new Color(245,245,220);    //#F5F5DC
    public static final Color Wheat                 = new Color(245,222,179);    //#F5DEB3
    public static final Color SandyBrown            = new Color(244,164,96);    //#F4A460
    public static final Color Tan           = new Color(210,180,140);    //#D2B48C
    public static final Color Chocolate             = new Color(210,105,30);    //#D2691E
    public static final Color Firebrick             = new Color(178,34,34);    //#B22222
    public static final Color Brown                 = new Color(165,42,42);    //#A52A2A
    public static final Color DarkSalmon            = new Color(233,150,122);    //#E9967A
    public static final Color Salmon                = new Color(250,128,114);    //#FA8072
    public static final Color LightSalmon           = new Color(255,160,122);    //#FFA07A
    public static final Color Orange                = new Color(255,165,0);    //#FFA500
    public static final Color DarkOrange            = new Color(255,140,0);    //#FF8C00
    public static final Color Coral                 = new Color(255,127,80);    //#FF7F50
    public static final Color LightCoral            = new Color(240,128,128);    //#F08080
    public static final Color Tomato                = new Color(255,99,71);    //#FF6347
    public static final Color OrangeRed             = new Color(255,69,0);    //#FF4500
    public static final Color Red           = new Color(255,0,0);    //#FF0000
    public static final Color HotPink               = new Color(255,105,180);    //#FF69B4
    public static final Color DeepPink              = new Color(255,20,147);    //#FF1493
    public static final Color Pink          = new Color(255,192,203);    //#FFC0CB
    public static final Color LightPink             = new Color(255,182,193);    //#FFB6C1
    public static final Color PaleVioletRed         = new Color(219,112,147);    //#DB7093
    public static final Color Maroon                = new Color(176,48,96);    //#B03060
    public static final Color MediumVioletRed       = new Color(199,21,133);    //#C71585
    public static final Color VioletRed             = new Color(208,32,144);    //#D02090
    public static final Color Magenta               = new Color(255,0,255);    //#FF00FF
    public static final Color Violet                = new Color(238,130,238);    //#EE82EE
    public static final Color Plum          = new Color(221,160,221);    //#DDA0DD
    public static final Color Orchid                = new Color(218,112,214);    //#DA70D6
    public static final Color MediumOrchid          = new Color(186,85,211);    //#BA55D3
    public static final Color DarkOrchid            = new Color(153,50,204);    //#9932CC
    public static final Color DarkViolet            = new Color(148,0,211);    //#9400D3
    public static final Color BlueViolet            = new Color(138,43,226);    //#8A2BE2
    public static final Color Purple                = new Color(160,32,240);    //#A020F0
    public static final Color MediumPurple          = new Color(147,112,219);    //#9370DB
    public static final Color Thistle               = new Color(216,191,216);    //#D8BFD8
    public static final Color Snow1                 = new Color(255,250,250);    //#FFFAFA
    public static final Color Snow2                 = new Color(238,233,233);    //#EEE9E9
    public static final Color Snow3                 = new Color(205,201,201);    //#CDC9C9
    public static final Color Snow4                 = new Color(139,137,137);    //#8B8989
    public static final Color Seashell1             = new Color(255,245,238);    //#FFF5EE
    public static final Color Seashell2             = new Color(238,229,222);    //#EEE5DE
    public static final Color Seashell3             = new Color(205,197,191);    //#CDC5BF
    public static final Color Seashell4             = new Color(139,134,130);    //#8B8682
    public static final Color AntiqueWhite1                 = new Color(255,239,219);    //#FFEFDB
    public static final Color AntiqueWhite2                 = new Color(238,223,204);    //#EEDFCC
    public static final Color AntiqueWhite3                 = new Color(205,192,176);    //#CDC0B0
    public static final Color AntiqueWhite4                 = new Color(139,131,120);    //#8B8378
    public static final Color Bisque1               = new Color(255,228,196);    //#FFE4C4
    public static final Color Bisque2               = new Color(238,213,183);    //#EED5B7
    public static final Color Bisque3               = new Color(205,183,158);    //#CDB79E
    public static final Color Bisque4               = new Color(139,125,107);    //#8B7D6B
    public static final Color PeachPuff1            = new Color(255,218,185);    //#FFDAB9
    public static final Color PeachPuff2            = new Color(238,203,173);    //#EECBAD
    public static final Color PeachPuff3            = new Color(205,175,149);    //#CDAF95
    public static final Color PeachPuff4            = new Color(139,119,101);    //#8B7765
    public static final Color NavajoWhite1          = new Color(255,222,173);    //#FFDEAD
    public static final Color NavajoWhite2          = new Color(238,207,161);    //#EECFA1
    public static final Color NavajoWhite3          = new Color(205,179,139);    //#CDB38B
    public static final Color NavajoWhite4          = new Color(139,121,94);    //#8B795E
    public static final Color LemonChiffon1                 = new Color(255,250,205);    //#FFFACD
    public static final Color LemonChiffon2                 = new Color(238,233,191);    //#EEE9BF
    public static final Color LemonChiffon3                 = new Color(205,201,165);    //#CDC9A5
    public static final Color LemonChiffon4                 = new Color(139,137,112);    //#8B8970
    public static final Color Cornsilk1             = new Color(255,248,220);    //#FFF8DC
    public static final Color Cornsilk2             = new Color(238,232,205);    //#EEE8CD
    public static final Color Cornsilk3             = new Color(205,200,177);    //#CDC8B1
    public static final Color Cornsilk4             = new Color(139,136,120);    //#8B8878
    public static final Color Ivory1                = new Color(255,255,240);    //#FFFFF0
    public static final Color Ivory2                = new Color(238,238,224);    //#EEEEE0
    public static final Color Ivory3                = new Color(205,205,193);    //#CDCDC1
    public static final Color Ivory4                = new Color(139,139,131);    //#8B8B83
    public static final Color Honeydew1             = new Color(240,255,240);    //#F0FFF0
    public static final Color Honeydew2             = new Color(224,238,224);    //#E0EEE0
    public static final Color Honeydew3             = new Color(193,205,193);    //#C1CDC1
    public static final Color Honeydew4             = new Color(131,139,131);    //#838B83
    public static final Color LavenderBlush1                = new Color(255,240,245);    //#FFF0F5
    public static final Color LavenderBlush2                = new Color(238,224,229);    //#EEE0E5
    public static final Color LavenderBlush3                = new Color(205,193,197);    //#CDC1C5
    public static final Color LavenderBlush4                = new Color(139,131,134);    //#8B8386
    public static final Color MistyRose1            = new Color(255,228,225);    //#FFE4E1
    public static final Color MistyRose2            = new Color(238,213,210);    //#EED5D2
    public static final Color MistyRose3            = new Color(205,183,181);    //#CDB7B5
    public static final Color MistyRose4            = new Color(139,125,123);    //#8B7D7B
    public static final Color Azure1                = new Color(240,255,255);    //#F0FFFF
    public static final Color Azure2                = new Color(224,238,238);    //#E0EEEE
    public static final Color Azure3                = new Color(193,205,205);    //#C1CDCD
    public static final Color Azure4                = new Color(131,139,139);    //#838B8B
    public static final Color SlateBlue1            = new Color(131,111,255);    //#836FFF
    public static final Color SlateBlue2            = new Color(122,103,238);    //#7A67EE
    public static final Color SlateBlue3            = new Color(105,89,205);    //#6959CD
    public static final Color SlateBlue4            = new Color(71,60,139);    //#473C8B
    public static final Color RoyalBlue1            = new Color(72,118,255);    //#4876FF
    public static final Color RoyalBlue2            = new Color(67,110,238);    //#436EEE
    public static final Color RoyalBlue3            = new Color(58,95,205);    //#3A5FCD
    public static final Color RoyalBlue4            = new Color(39,64,139);    //#27408B
    public static final Color Blue1                 = new Color(0,0,255);    //#0000FF
    public static final Color Blue2                 = new Color(0,0,238);    //#0000EE
    public static final Color Blue3                 = new Color(0,0,205);    //#0000CD
    public static final Color Blue4                 = new Color(0,0,139);    //#00008B
    public static final Color DodgerBlue1           = new Color(30,144,255);    //#1E90FF
    public static final Color DodgerBlue2           = new Color(28,134,238);    //#1C86EE
    public static final Color DodgerBlue3           = new Color(24,116,205);    //#1874CD
    public static final Color DodgerBlue4           = new Color(16,78,139);    //#104E8B
    public static final Color SteelBlue1            = new Color(99,184,255);    //#63B8FF
    public static final Color SteelBlue2            = new Color(92,172,238);    //#5CACEE
    public static final Color SteelBlue3            = new Color(79,148,205);    //#4F94CD
    public static final Color SteelBlue4            = new Color(54,100,139);    //#36648B
    public static final Color DeepSkyBlue1          = new Color(0,191,255);    //#00BFFF
    public static final Color DeepSkyBlue2          = new Color(0,178,238);    //#00B2EE
    public static final Color DeepSkyBlue3          = new Color(0,154,205);    //#009ACD
    public static final Color DeepSkyBlue4          = new Color(0,104,139);    //#00688B
    public static final Color SkyBlue1              = new Color(135,206,255);    //#87CEFF
    public static final Color SkyBlue2              = new Color(126,192,238);    //#7EC0EE
    public static final Color SkyBlue3              = new Color(108,166,205);    //#6CA6CD
    public static final Color SkyBlue4              = new Color(74,112,139);    //#4A708B
    public static final Color LightSkyBlue1                 = new Color(176,226,255);    //#B0E2FF
    public static final Color LightSkyBlue2                 = new Color(164,211,238);    //#A4D3EE
    public static final Color LightSkyBlue3                 = new Color(141,182,205);    //#8DB6CD
    public static final Color LightSkyBlue4                 = new Color(96,123,139);    //#607B8B
    public static final Color SlateGray1            = new Color(198,226,255);    //#C6E2FF
    public static final Color SlateGray2            = new Color(185,211,238);    //#B9D3EE
    public static final Color SlateGray3            = new Color(159,182,205);    //#9FB6CD
    public static final Color SlateGray4            = new Color(108,123,139);    //#6C7B8B
    public static final Color LightSteelBlue1               = new Color(202,225,255);    //#CAE1FF
    public static final Color LightSteelBlue2               = new Color(188,210,238);    //#BCD2EE
    public static final Color LightSteelBlue3               = new Color(162,181,205);    //#A2B5CD
    public static final Color LightSteelBlue4               = new Color(110,123,139);    //#6E7B8B
    public static final Color LightBlue1            = new Color(191,239,255);    //#BFEFFF
    public static final Color LightBlue2            = new Color(178,223,238);    //#B2DFEE
    public static final Color LightBlue3            = new Color(154,192,205);    //#9AC0CD
    public static final Color LightBlue4            = new Color(104,131,139);    //#68838B
    public static final Color LightCyan1            = new Color(224,255,255);    //#E0FFFF
    public static final Color LightCyan2            = new Color(209,238,238);    //#D1EEEE
    public static final Color LightCyan3            = new Color(180,205,205);    //#B4CDCD
    public static final Color LightCyan4            = new Color(122,139,139);    //#7A8B8B
    public static final Color PaleTurquoise1                = new Color(187,255,255);    //#BBFFFF
    public static final Color PaleTurquoise2                = new Color(174,238,238);    //#AEEEEE
    public static final Color PaleTurquoise3                = new Color(150,205,205);    //#96CDCD
    public static final Color PaleTurquoise4                = new Color(102,139,139);    //#668B8B
    public static final Color CadetBlue1            = new Color(152,245,255);    //#98F5FF
    public static final Color CadetBlue2            = new Color(142,229,238);    //#8EE5EE
    public static final Color CadetBlue3            = new Color(122,197,205);    //#7AC5CD
    public static final Color CadetBlue4            = new Color(83,134,139);    //#53868B
    public static final Color Turquoise1            = new Color(0,245,255);    //#00F5FF
    public static final Color Turquoise2            = new Color(0,229,238);    //#00E5EE
    public static final Color Turquoise3            = new Color(0,197,205);    //#00C5CD
    public static final Color Turquoise4            = new Color(0,134,139);    //#00868B
    public static final Color Cyan1                 = new Color(0,255,255);    //#00FFFF
    public static final Color Cyan2                 = new Color(0,238,238);    //#00EEEE
    public static final Color Cyan3                 = new Color(0,205,205);    //#00CDCD
    public static final Color Cyan4                 = new Color(0,139,139);    //#008B8B
    public static final Color DarkSlateGray1                = new Color(151,255,255);    //#97FFFF
    public static final Color DarkSlateGray2                = new Color(141,238,238);    //#8DEEEE
    public static final Color DarkSlateGray3                = new Color(121,205,205);    //#79CDCD
    public static final Color DarkSlateGray4                = new Color(82,139,139);    //#528B8B
    public static final Color Aquamarine1           = new Color(127,255,212);    //#7FFFD4
    public static final Color Aquamarine2           = new Color(118,238,198);    //#76EEC6
    public static final Color Aquamarine3           = new Color(102,205,170);    //#66CDAA
    public static final Color Aquamarine4           = new Color(69,139,116);    //#458B74
    public static final Color DarkSeaGreen1                 = new Color(193,255,193);    //#C1FFC1
    public static final Color DarkSeaGreen2                 = new Color(180,238,180);    //#B4EEB4
    public static final Color DarkSeaGreen3                 = new Color(155,205,155);    //#9BCD9B
    public static final Color DarkSeaGreen4                 = new Color(105,139,105);    //#698B69
    public static final Color SeaGreen1             = new Color(84,255,159);    //#54FF9F
    public static final Color SeaGreen2             = new Color(78,238,148);    //#4EEE94
    public static final Color SeaGreen3             = new Color(67,205,128);    //#43CD80
    public static final Color SeaGreen4             = new Color(46,139,87);    //#2E8B57
    public static final Color PaleGreen1            = new Color(154,255,154);    //#9AFF9A
    public static final Color PaleGreen2            = new Color(144,238,144);    //#90EE90
    public static final Color PaleGreen3            = new Color(124,205,124);    //#7CCD7C
    public static final Color PaleGreen4            = new Color(84,139,84);    //#548B54
    public static final Color SpringGreen1          = new Color(0,255,127);    //#00FF7F
    public static final Color SpringGreen2          = new Color(0,238,118);    //#00EE76
    public static final Color SpringGreen3          = new Color(0,205,102);    //#00CD66
    public static final Color SpringGreen4          = new Color(0,139,69);    //#008B45
    public static final Color Green1                = new Color(0,255,0);    //#00FF00
    public static final Color Green2                = new Color(0,238,0);    //#00EE00
    public static final Color Green3                = new Color(0,205,0);    //#00CD00
    public static final Color Green4                = new Color(0,139,0);    //#008B00
    public static final Color Chartreuse1           = new Color(127,255,0);    //#7FFF00
    public static final Color Chartreuse2           = new Color(118,238,0);    //#76EE00
    public static final Color Chartreuse3           = new Color(102,205,0);    //#66CD00
    public static final Color Chartreuse4           = new Color(69,139,0);    //#458B00
    public static final Color OliveDrab1            = new Color(192,255,62);    //#C0FF3E
    public static final Color OliveDrab2            = new Color(179,238,58);    //#B3EE3A
    public static final Color OliveDrab3            = new Color(154,205,50);    //#9ACD32
    public static final Color OliveDrab4            = new Color(105,139,34);    //#698B22
    public static final Color DarkOliveGreen1               = new Color(202,255,112);    //#CAFF70
    public static final Color DarkOliveGreen2               = new Color(188,238,104);    //#BCEE68
    public static final Color DarkOliveGreen3               = new Color(162,205,90);    //#A2CD5A
    public static final Color DarkOliveGreen4               = new Color(110,139,61);    //#6E8B3D
    public static final Color Khaki1                = new Color(255,246,143);    //#FFF68F
    public static final Color Khaki2                = new Color(238,230,133);    //#EEE685
    public static final Color Khaki3                = new Color(205,198,115);    //#CDC673
    public static final Color Khaki4                = new Color(139,134,78);    //#8B864E
    public static final Color LightGoldenrod1               = new Color(255,236,139);    //#FFEC8B
    public static final Color LightGoldenrod2               = new Color(238,220,130);    //#EEDC82
    public static final Color LightGoldenrod3               = new Color(205,190,112);    //#CDBE70
    public static final Color LightGoldenrod4               = new Color(139,129,76);    //#8B814C
    public static final Color LightYellow1          = new Color(255,255,224);    //#FFFFE0
    public static final Color LightYellow2          = new Color(238,238,209);    //#EEEED1
    public static final Color LightYellow3          = new Color(205,205,180);    //#CDCDB4
    public static final Color LightYellow4          = new Color(139,139,122);    //#8B8B7A
    public static final Color Yellow1               = new Color(255,255,0);    //#FFFF00
    public static final Color Yellow2               = new Color(238,238,0);    //#EEEE00
    public static final Color Yellow3               = new Color(205,205,0);    //#CDCD00
    public static final Color Yellow4               = new Color(139,139,0);    //#8B8B00
    public static final Color Gold1                 = new Color(255,215,0);    //#FFD700
    public static final Color Gold2                 = new Color(238,201,0);    //#EEC900
    public static final Color Gold3                 = new Color(205,173,0);    //#CDAD00
    public static final Color Gold4                 = new Color(139,117,0);    //#8B7500
    public static final Color Goldenrod1            = new Color(255,193,37);    //#FFC125
    public static final Color Goldenrod2            = new Color(238,180,34);    //#EEB422
    public static final Color Goldenrod3            = new Color(205,155,29);    //#CD9B1D
    public static final Color Goldenrod4            = new Color(139,105,20);    //#8B6914
    public static final Color DarkGoldenrod1        = new Color(255,185,15);    //#FFB90F
    public static final Color DarkGoldenrod2        = new Color(238,173,14);    //#EEAD0E
    public static final Color DarkGoldenrod3        = new Color(205,149,12);    //#CD950C
    public static final Color DarkGoldenrod4        = new Color(139,101,8);    //#8B658B
    public static final Color RosyBrown1            = new Color(255,193,193);    //#FFC1C1
    public static final Color RosyBrown2            = new Color(238,180,180);    //#EEB4B4
    public static final Color RosyBrown3            = new Color(205,155,155);    //#CD9B9B
    public static final Color RosyBrown4            = new Color(139,105,105);    //#8B6969
    public static final Color IndianRed1            = new Color(255,106,106);    //#FF6A6A
    public static final Color IndianRed2            = new Color(238,99,99);    //#EE6363
    public static final Color IndianRed3            = new Color(205,85,85);    //#CD5555
    public static final Color IndianRed4            = new Color(139,58,58);    //#8B3A3A
    public static final Color Sienna1               = new Color(255,130,71);    //#FF8247
    public static final Color Sienna2               = new Color(238,121,66);    //#EE7942
    public static final Color Sienna3               = new Color(205,104,57);    //#CD6839
    public static final Color Sienna4               = new Color(139,71,38);    //#8B4726
    public static final Color Burlywood1            = new Color(255,211,155);    //#FFD39B
    public static final Color Burlywood2            = new Color(238,197,145);    //#EEC591
    public static final Color Burlywood3            = new Color(205,170,125);    //#CDAA7D
    public static final Color Burlywood4            = new Color(139,115,85);    //#8B7355
    public static final Color Wheat1                = new Color(255,231,186);    //#FFE7BA
    public static final Color Wheat2                = new Color(238,216,174);    //#EED8AE
    public static final Color Wheat3                = new Color(205,186,150);    //#CDBA96
    public static final Color Wheat4                = new Color(139,126,102);    //#8B7E66
    public static final Color Tan1                  = new Color(255,165,79);    //#FFA54F
    public static final Color Tan2                  = new Color(238,154,73);    //#EE9A49
    public static final Color Tan3                  = new Color(205,133,63);    //#CD853F
    public static final Color Tan4                  = new Color(139,90,43);    //#8B5A2B
    public static final Color Chocolate1            = new Color(255,127,36);    //#FF7F24
    public static final Color Chocolate2            = new Color(238,118,33);    //#EE7621
    public static final Color Chocolate3            = new Color(205,102,29);    //#CD661D
    public static final Color Chocolate4            = new Color(139,69,19);    //#8B4513
    public static final Color Firebrick1            = new Color(255,48,48);    //#FF3030
    public static final Color Firebrick2            = new Color(238,44,44);    //#EE2C2C
    public static final Color Firebrick3            = new Color(205,38,38);    //#CD2626
    public static final Color Firebrick4            = new Color(139,26,26);    //#8B1A1A
    public static final Color Brown1                = new Color(255,64,64);    //#FF4040
    public static final Color Brown2                = new Color(238,59,59);    //#EE3B3B
    public static final Color Brown3                = new Color(205,51,51);    //#CD3333
    public static final Color Brown4                = new Color(139,35,35);    //#8B2323
    public static final Color Salmon1               = new Color(255,140,105);    //#FF8C69
    public static final Color Salmon2               = new Color(238,130,98);    //#EE8262
    public static final Color Salmon3               = new Color(205,112,84);    //#CD7054
    public static final Color Salmon4               = new Color(139,76,57);    //#8B4C39
    public static final Color LightSalmon1          = new Color(255,160,122);    //#FFA07A
    public static final Color LightSalmon2          = new Color(238,149,114);    //#EE9572
    public static final Color LightSalmon3          = new Color(205,129,98);    //#CD8162
    public static final Color LightSalmon4          = new Color(139,87,66);    //#8B5742
    public static final Color Orange1               = new Color(255,165,0);    //#FFA500
    public static final Color Orange2               = new Color(238,154,0);    //#EE9A00
    public static final Color Orange3               = new Color(205,133,0);    //#CD8500
    public static final Color Orange4               = new Color(139,90,0);    //#8B5A00
    public static final Color DarkOrange1           = new Color(255,127,0);    //#FF7F00
    public static final Color DarkOrange2           = new Color(238,118,0);    //#EE7600
    public static final Color DarkOrange3           = new Color(205,102,0);    //#CD6600
    public static final Color DarkOrange4           = new Color(139,69,0);    //#8B4500
    public static final Color Coral1                = new Color(255,114,86);    //#FF7256
    public static final Color Coral2                = new Color(238,106,80);    //#EE6A50
    public static final Color Coral3                = new Color(205,91,69);    //#CD5B45
    public static final Color Coral4                = new Color(139,62,47);    //#8B3E2F
    public static final Color Tomato1               = new Color(255,99,71);    //#FF6347
    public static final Color Tomato2               = new Color(238,92,66);    //#EE5C42
    public static final Color Tomato3               = new Color(205,79,57);    //#CD4F39
    public static final Color Tomato4               = new Color(139,54,38);    //#8B3626
    public static final Color OrangeRed1            = new Color(255,69,0);    //#FF4500
    public static final Color OrangeRed2            = new Color(238,64,0);    //#EE4000
    public static final Color OrangeRed3            = new Color(205,55,0);    //#CD3700
    public static final Color OrangeRed4            = new Color(139,37,0);    //#8B2500
    public static final Color Red1                  = new Color(255,0,0);    //#FF0000
    public static final Color Red2                  = new Color(238,0,0);    //#EE0000
    public static final Color Red3                  = new Color(205,0,0);    //#CD0000
    public static final Color Red4                  = new Color(139,0,0);    //#8B0000
    public static final Color DeepPink1             = new Color(255,20,147);    //#FF1493
    public static final Color DeepPink2             = new Color(238,18,137);    //#EE1289
    public static final Color DeepPink3             = new Color(205,16,118);    //#CD1076
    public static final Color DeepPink4             = new Color(139,10,80);    //#8B0A50
    public static final Color HotPink1              = new Color(255,110,180);    //#FF6EB4
    public static final Color HotPink2              = new Color(238,106,167);    //#EE6AA7
    public static final Color HotPink3              = new Color(205,96,144);    //#CD6090
    public static final Color HotPink4              = new Color(139,58,98);    //#8B3A62
    public static final Color Pink1                 = new Color(255,181,197);    //#FFB5C5
    public static final Color Pink2                 = new Color(238,169,184);    //#EEA9B8
    public static final Color Pink3                 = new Color(205,145,158);    //#CD919E
    public static final Color Pink4                 = new Color(139,99,108);    //#8B636C
    public static final Color LightPink1            = new Color(255,174,185);    //#FFAEB9
    public static final Color LightPink2            = new Color(238,162,173);    //#EEA2AD
    public static final Color LightPink3            = new Color(205,140,149);    //#CD8C95
    public static final Color LightPink4            = new Color(139,95,101);    //#8B5F65
    public static final Color PaleVioletRed1        = new Color(255,130,171);    //#FF82AB
    public static final Color PaleVioletRed2        = new Color(238,121,159);    //#EE799F
    public static final Color PaleVioletRed3        = new Color(205,104,137);    //#CD6889
    public static final Color PaleVioletRed4        = new Color(139,71,93);    //#8B475D
    public static final Color Maroon1               = new Color(255,52,179);    //#FF34B3
    public static final Color Maroon2               = new Color(238,48,167);    //#EE30A7
    public static final Color Maroon3               = new Color(205,41,144);    //#CD2990
    public static final Color Maroon4               = new Color(139,28,98);    //#8B1C62
    public static final Color VioletRed1            = new Color(255,62,150);    //#FF3E96
    public static final Color VioletRed2            = new Color(238,58,140);    //#EE3A8C
    public static final Color VioletRed3            = new Color(205,50,120);    //#CD3278
    public static final Color VioletRed4            = new Color(139,34,82);    //#8B2252
    public static final Color Magenta1              = new Color(255,0,255);    //#FF00FF
    public static final Color Magenta2              = new Color(238,0,238);    //#EE00EE
    public static final Color Magenta3              = new Color(205,0,205);    //#CD00CD
    public static final Color Magenta4              = new Color(139,0,139);    //#8B008B
    public static final Color Orchid1               = new Color(255,131,250);    //#FF83FA
    public static final Color Orchid2               = new Color(238,122,233);    //#EE7AE9
    public static final Color Orchid3               = new Color(205,105,201);    //#CD69C9
    public static final Color Orchid4               = new Color(139,71,137);    //#8B4789
    public static final Color Plum1                 = new Color(255,187,255);    //#FFBBFF
    public static final Color Plum2                 = new Color(238,174,238);    //#EEAEEE
    public static final Color Plum3                 = new Color(205,150,205);    //#CD96CD
    public static final Color Plum4                 = new Color(139,102,139);    //#8B668B
    public static final Color MediumOrchid1         = new Color(224,102,255);    //#E066FF
    public static final Color MediumOrchid2         = new Color(209,95,238);    //#D15FEE
    public static final Color MediumOrchid3         = new Color(180,82,205);    //#B452CD
    public static final Color MediumOrchid4         = new Color(122,55,139);    //#7A378B
    public static final Color DarkOrchid1           = new Color(191,62,255);    //#BF3EFF
    public static final Color DarkOrchid2           = new Color(178,58,238);    //#B23AEE
    public static final Color DarkOrchid3           = new Color(154,50,205);    //#9A32CD
    public static final Color DarkOrchid4           = new Color(104,34,139);    //#68228B
    public static final Color Purple1               = new Color(155,48,255);    //#9B30FF
    public static final Color Purple2               = new Color(145,44,238);    //#912CEE
    public static final Color Purple3               = new Color(125,38,205);    //#7D26CD
    public static final Color Purple4               = new Color(85,26,139);    //#551A8B
    public static final Color MediumPurple1         = new Color(171,130,255);    //#AB82FF
    public static final Color MediumPurple2         = new Color(159,121,238);    //#9F79EE
    public static final Color MediumPurple3         = new Color(137,104,205);    //#8968CD
    public static final Color MediumPurple4         = new Color(93,71,139);    //#5D478B
    public static final Color Thistle1              = new Color(255,225,255);    //#FFE1FF
    public static final Color Thistle2              = new Color(238,210,238);    //#EED2EE
    public static final Color Thistle3              = new Color(205,181,205);    //#CDB5CD
    public static final Color Thistle4              = new Color(139,123,139);    //#8B7B8B
    public static final Color grey11                = new Color(28,28,28);    //#1C1C1C
    public static final Color grey21                = new Color(54,54,54);    //#363636
    public static final Color grey31                = new Color(79,79,79);    //#4F4F4F
    public static final Color grey41                = new Color(105,105,105);    //#696969
    public static final Color grey51                = new Color(130,130,130);    //#828282
    public static final Color grey61                = new Color(156,156,156);    //#9C9C9C
    public static final Color grey71                = new Color(181,181,181);    //#B5B5B5
    public static final Color gray81                = new Color(207,207,207);    //#CFCFCF
    public static final Color gray91                = new Color(232,232,232);    //#E8E8E8
    public static final Color DarkGrey              = new Color(169,169,169);    //#A9A9A9
    public static final Color DarkBlue              = new Color(0,0,139);    //#00008B
    public static final Color DarkCyan              = new Color(0,139,139);    //#008B8B
    public static final Color DarkMagenta           = new Color(139,0,139);    //#8B008B
    public static final Color DarkRed               = new Color(139,0,0);    //#8B0000
    
    private static Color[] allColors;;
    
    private Colors()
    {
        
    }
    
    public static Color[] htmlColors()
    {
        if ( allColors == null )
        {
            ArrayList<Color> list = new ArrayList<Color>();
            try
            {     
                Field[] fields = Colors.class.getFields();
                for (int i = 0; i < fields.length; i++ )
                {
                    list.add((Color)(fields[i].get(null)));
                } 
            }
            catch (Exception e)
            {
            }

            allColors = list.toArray(new Color[0]);
        }
        
        return allColors;
    }
    
    
    public static Color colorValue(String hex)
    {
        return new Color(Integer.parseInt(hex.replaceAll("#",""),16));
    }
    
    public static String hexValue(Color color)
    {
        int[] values = { color.getRed(), 
        color.getGreen(),
        color.getBlue() };
        
        StringBuffer buffer = new StringBuffer();
        for ( int i = 0; i < values.length; i++ )
        {
            if ( values[i] < 16 )
            {
                buffer.append(0);
            }
            
            buffer.append(Integer.toHexString(values[i]));
        }
        
        return buffer.toString();
    }
}
