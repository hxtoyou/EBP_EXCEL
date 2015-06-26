(function(b) {
    var a = {};
    //默认配置定义
    a.config = {
        editable: true,
        lineHeight: 15,
        basePath: "",
        rect: {
            attr: {
                x: 10,
                y: 10,
                width: 100,
                height: 50,
                r: 5,
                fill: "90-#fff-#C0C0C0",
                stroke: "#000",
                "stroke-width": 1
            },
            showType: "image&text",
            type: "state",
            name: {
                text: "state",
                "font-style": "italic"
            },
            text: {
                text: "状态",
                "font-size": 13
            },
            margin: 5,
            props: [],
            img: {}
        },
        path: {
            attr: {
                path: {
                    path: "M10 10L100 100",
                    stroke: "#808080",
                    fill: "none",
                    "stroke-width": 2
                },
                arrow: {
                    path: "M10 10L10 10",
                    stroke: "#808080",
                    fill: "#808080",
                    "stroke-width": 2,
                    radius: 4
                },
                fromDot: {
                    width: 5,
                    height: 5,
                    stroke: "#fff",
                    fill: "#000",
                    cursor: "move",
                    "stroke-width": 2
                },
                toDot: {
                    width: 5,
                    height: 5,
                    stroke: "#fff",
                    fill: "#000",
                    cursor: "move",
                    "stroke-width": 2
                },
                bigDot: {
                    width: 5,
                    height: 5,
                    stroke: "#fff",
                    fill: "#000",
                    cursor: "move",
                    "stroke-width": 2
                },
                smallDot: {
                    width: 5,
                    height: 5,
                    stroke: "#fff",
                    fill: "#000",
                    cursor: "move",
                    "stroke-width": 3
                }
            },
            text: {
                text: "TO {to}",
                cursor: "move",
                background: "#000"
            },
            textPos: {
                x: 0,
                y: -10
            },
            props: {
                text: {
                    name: "text",
                    label: "显示",
                    value: "",
                    editor: function() {
                        return new a.editors.textEditor()
                    }
                },
                jsExp: {
                    name: "jsExp",
                    label: "js表达式",
                    value: "",
                    editor: function() {
                        return new a.editors.textAreaEditor([{style:"width:195px;height:150px;resize:none;"}]);
                    }
                }
            }
        },
        tools: {
            attr: {
                left: 10,
                top: 10
            },
            pointer: {},
            path: {},
            states: {},
            save: {
                onclick: function(c) {
                    alert(c)
                }
            }
        },
        props: {
            attr: {
                top: 10,
                right: 30
            },
            props: {}
        },
        restore: "",
        activeRects: {
            rects: [],
            rectAttr: {
                stroke: "#00ff00",
                "stroke-width": 2
            }
        },
        historyRects: {
            rects: [],
            pathAttr: {
                path: {
                    stroke: "#00ff00"
                },
                arrow: {
                    stroke: "#00ff00",
                    fill: "#00ff00"
                }
            }
        },
        endRects: {
            rects: [],
            rectAttr: {
                stroke: "#ff0000",
                "stroke-width": 2
            }
        }
    };
    //公用方法
    a.util = {
    		//判断是否是条直线
        isLine: function(leftDotPos, centerDotPos, rightDotPos) {
            var lineSlope, calculateY;
            if ((leftDotPos.x - rightDotPos.x) == 0) {
                lineSlope = 1
            } else {
                lineSlope = (leftDotPos.y - rightDotPos.y) / (leftDotPos.x - rightDotPos.x)
            }
            calculateY = (centerDotPos.x - rightDotPos.x) * lineSlope + rightDotPos.y;
            if ((centerDotPos.y - calculateY) < 10 && (centerDotPos.y - calculateY) > -10) {
                centerDotPos.y = calculateY;
                return true
            }
            return false
        },
        //获取中心点坐标
        center: function(leftDotPos, rightDotPos) {
            return {
                x: (leftDotPos.x - rightDotPos.x) / 2 + rightDotPos.x,
                y: (leftDotPos.y - rightDotPos.y) / 2 + rightDotPos.y
            }
        },
        //获取下一个ID
        nextId: (function() {
            var c = 0;
            return function(nt) {
            	  c = nt||c;
                return++c
            }
        })(),
        //获取节点和路径的连接点
        connPoint: function(_nodeBox, _dotPos) {
        		//路径点的坐标
            var dotPos = _dotPos,
            //节点连线坐标
            nodePos = {
                x: _nodeBox.x + _nodeBox.width / 2,
                y: _nodeBox.y + _nodeBox.height / 2
            };
            //获取两点之间的斜率，如果两点的x坐标相等，斜率为0
            var lineSlope = (nodePos.y - dotPos.y) / (nodePos.x - dotPos.x);
            lineSlope = isNaN(lineSlope) ? 0 : lineSlope;
            //获取节点的高度和宽度的比
            var node_h_w_ratio = _nodeBox.height / _nodeBox.width;
            //路径点和节点的y坐标的比较
            var yFlag = dotPos.y < nodePos.y ? -1 : 1,
            //路径点和节点的x坐标的比较
            xFlag = dotPos.x < nodePos.x ? -1 : 1,
            //返回的连接点的y坐标
            connDotY,
            //返回的连接点的x坐标
            connDotX;
            if (Math.abs(lineSlope) > node_h_w_ratio && yFlag == -1) {
                connDotY = nodePos.y - _nodeBox.height / 2;
                connDotX = nodePos.x + yFlag * _nodeBox.height / 2 / lineSlope
            } else {
                if (Math.abs(lineSlope) > node_h_w_ratio && yFlag == 1) {
                    connDotY = nodePos.y + _nodeBox.height / 2;
                    connDotX = nodePos.x + yFlag * _nodeBox.height / 2 / lineSlope
                } else {
                    if (Math.abs(lineSlope) < node_h_w_ratio && xFlag == -1) {
                        connDotY = nodePos.y + xFlag * _nodeBox.width / 2 * lineSlope;
                        connDotX = nodePos.x - _nodeBox.width / 2
                    } else {
                        if (Math.abs(lineSlope) < node_h_w_ratio && xFlag == 1) {
                            connDotY = nodePos.y + _nodeBox.width / 2 * lineSlope;
                            connDotX = nodePos.x + _nodeBox.width / 2
                        }
                    }
                }
            }
            return {
                x: connDotX,
                y: connDotY
            }
        },
        //获取连接线的箭头
        arrow: function(fromDotPos, toDotPos, radius) {
        		//计算出线的角度
            var angle = Math.atan2(fromDotPos.y - toDotPos.y, toDotPos.x - fromDotPos.x) * (180 / Math.PI);
            //计算出箭头的点的坐标
            var radiusX = toDotPos.x - radius * Math.cos(angle * (Math.PI / 180));
            var radiusY = toDotPos.y + radius * Math.sin(angle * (Math.PI / 180));
            var dot2PosX = radiusX + radius * Math.cos((angle + 120) * (Math.PI / 180));
            var dot2PosY = radiusY - radius * Math.sin((angle + 120) * (Math.PI / 180));
            var dot3PosX = radiusX + radius * Math.cos((angle + 240) * (Math.PI / 180));
            var dot3PosY = radiusY - radius * Math.sin((angle + 240) * (Math.PI / 180));
            return [toDotPos, {
                x: dot2PosX,
                y: dot2PosY
            },
            {
                x: dot3PosX,
                y: dot3PosY
            }]
        }
    };
    //节点
    a.rect = function(_cfg, _paper) {
        var rectObj = this,
      //  rectId = "rect" + a.util.nextId(),
      	rectId,
        rectCfg = b.extend(true, {},
        a.config.rect, _cfg),
        paper = _paper,
        rectNode,
        rectImg,
        rectTextTop,
        rectTextBottom,
        dragStartX,
        dragStartY;
        //节点的展示方式是node方式，节点的ID根据节点的属性的nodeId生成。
        if(rectCfg.showType == "node"){
        		if(!rectCfg.props.nodeId || rectCfg.props.nodeId.value == ""){//属性的nodeId不存在的时候，节点的ID以自增的方式生成
	        		var rectId = a.util.nextId();
	        		//节点ID是4位数字，不足的前面补0
	        		var nodeId = "";
	        		for(var idx = 0;idx < 4 - (""+rectId).length; idx++){
	        			nodeId += "0";
	        		}
	        		nodeId = nodeId + rectId;
	        		if(rectCfg.props.nodeId){
		        		rectCfg.props.nodeId.value = nodeId;
	        		}else{
	        			var row = {};
	        			row.value = nodeId;
								rectCfg.props.nodeId = row;
	        		}
	        		rectId = "rect" + nodeId;
        		}else{//属性的nodeId存在的时候，节点的ID和属性的nodeId的值保持一致
        			if(rectCfg.props.nodeId.value == "0000" || rectCfg.props.nodeId.value == "9999"){//开始或者结束的节点
        				rectId = "rect" + rectCfg.props.nodeId.value;
        			}else{
        				rectId = "rect" + rectCfg.props.nodeId.value;
        				a.util.nextId(rectCfg.props.nodeId.value - 1);
         			}
        		}
        }else{//其他的展示方式，以自增的方式生成节点的ID
        	rectId = "rect" + a.util.nextId();
        }
        //生成一个矩形：左上角的x坐标，左上角的y坐标，宽度，高度，圆角半径
        rectNode = paper.rect(rectCfg.attr.x, rectCfg.attr.y, rectCfg.attr.width, rectCfg.attr.height, rectCfg.attr.r).hide().attr(rectCfg.attr);
        //生成一个图像(位于矩形内的左边)：源图像的URI，x坐标，y坐标，图像的宽度，图像的高度
        rectImg = paper.image(a.config.basePath + rectCfg.img.src, rectCfg.attr.x + rectCfg.img.width / 2, rectCfg.attr.y + (rectCfg.attr.height - rectCfg.img.height) / 2, rectCfg.img.width, rectCfg.img.height).hide();
        //生成一个文本(位于矩形内的上边)：x坐标，y坐标，文本字符串
        rectTextTop = paper.text(rectCfg.attr.x + rectCfg.img.width + (rectCfg.attr.width - rectCfg.img.width) / 2, rectCfg.attr.y + a.config.lineHeight / 2, rectCfg.name.text).hide().attr(rectCfg.name);
        //生成一个文本(位于矩形内的下边)：x坐标，y坐标，文本字符串
        rectTextBottom = paper.text(rectCfg.attr.x + rectCfg.img.width + (rectCfg.attr.width - rectCfg.img.width) / 2, rectCfg.attr.y + (rectCfg.attr.height - a.config.lineHeight) / 2 + a.config.lineHeight, rectCfg.text.text).hide().attr(rectCfg.text);
        rectNode.drag(function(dx, dy) {//移动处理函数：相对起始点的x位移，相对起始点的y位移
            //节点的拖拽的移动处理函数
            rectDragOnMove(dx, dy);
        },
        function() {//拖拽开始的处理函数
            rectDragOnStart();
        },
        function() {//拖拽结束处理函数
            rectDragOnEnd();
        });
        rectImg.drag(function(dx, dy) {//移动处理函数：相对起始点的x位移，相对起始点的y位移
             //节点的拖拽的移动处理函数
           rectDragOnMove(dx, dy);
        },
        function() {//拖拽开始的处理函数
            rectDragOnStart();
        },
        function() {//拖拽结束处理函数
            rectDragOnEnd();
        });
        rectTextTop.drag(function(dx, dy) {//移动处理函数：相对起始点的x位移，相对起始点的y位移
            rectDragOnMove(dx, dy);
        },
        function() {//拖拽开始的处理函数
            rectDragOnStart();
        },
        function() {//拖拽结束处理函数
            rectDragOnEnd();
        });
        rectTextBottom.drag(function(dx, dy) {//移动处理函数：相对起始点的x位移，相对起始点的y位移
            rectDragOnMove(dx, dy);
        },
        function() {//拖拽开始的处理函数
            rectDragOnStart();
        },
        function() {//拖拽结束处理函数
            rectDragOnEnd();
        });
        //节点的拖拽的移动处理函数
        var rectDragOnMove = function(dx, dy) {
            if (!a.config.editable) {
                return
            }
            var posX = (dragStartX + dx);
            var posY = (dragStartY + dy);
            rectBoxInfo.x = posX - rectCfg.margin;
            rectBoxInfo.y = posY - rectCfg.margin;
            parseRect();
        };
        //节点的拖拽的拖拽开始的处理函数
        var rectDragOnStart = function() {
            dragStartX = rectNode.attr("x");
            dragStartY = rectNode.attr("y");
            rectNode.attr({
                opacity: 0.5
            });
            rectImg.attr({
                opacity: 0.5
            });
            rectTextBottom.attr({
                opacity: 0.5
            })
        };
        //节点的拖拽的拖拽结束处理函数
        var rectDragOnEnd = function() {
            rectNode.attr({
                opacity: 1
            });
            rectImg.attr({
                opacity: 1
            });
            rectTextBottom.attr({
                opacity: 1
            })
        };
        var rectBoxPath;//节点选中时的可进行调整大小的边界框的路径
        var rectBoxNodes = {};//节点选中时的可进行调整的边界框的点的集合
        var sideLen = 5;//边界框的节点的边长
        //节点可进行调整的边界框的信息
        var rectBoxInfo = {
            x: rectCfg.attr.x - rectCfg.margin,
            y: rectCfg.attr.y - rectCfg.margin,
            width: rectCfg.attr.width + rectCfg.margin * 2,
            height: rectCfg.attr.height + rectCfg.margin * 2
        };
        rectBoxPath = paper.path("M0 0L1 1").hide();
        //边界框的居上的点
        rectBoxNodes.t = paper.rect(0, 0, sideLen, sideLen).attr({
            fill: "#000",
            stroke: "#fff",
            cursor: "s-resize"
        }).hide().drag(function(dx, dy) {
            boxNodeDragOnMove(dx, dy, "t")
        },
        function() {
            changeBoxNodePos(this.attr("x") + sideLen / 2, this.attr("y") + sideLen / 2, "t")
        },
        function() {});
        //边界框的左上的点
        rectBoxNodes.lt = paper.rect(0, 0, sideLen, sideLen).attr({
            fill: "#000",
            stroke: "#fff",
            cursor: "nw-resize"
        }).hide().drag(function(dx, dy) {
            boxNodeDragOnMove(dx, dy, "lt")
        },
        function() {
            changeBoxNodePos(this.attr("x") + sideLen / 2, this.attr("y") + sideLen / 2, "lt")
        },
        function() {});
        //边界框的左边的点
        rectBoxNodes.l = paper.rect(0, 0, sideLen, sideLen).attr({
            fill: "#000",
            stroke: "#fff",
            cursor: "w-resize"
        }).hide().drag(function(dx, dy) {
            boxNodeDragOnMove(dx, dy, "l")
        },
        function() {
            changeBoxNodePos(this.attr("x") + sideLen / 2, this.attr("y") + sideLen / 2, "l")
        },
        function() {});
        //边界框的左下的点
        rectBoxNodes.lb = paper.rect(0, 0, sideLen, sideLen).attr({
            fill: "#000",
            stroke: "#fff",
            cursor: "sw-resize"
        }).hide().drag(function(dx, dy) {
            boxNodeDragOnMove(dx, dy, "lb")
        },
        function() {
            changeBoxNodePos(this.attr("x") + sideLen / 2, this.attr("y") + sideLen / 2, "lb")
        },
        function() {});
        //边界框的下面的点
        rectBoxNodes.b = paper.rect(0, 0, sideLen, sideLen).attr({
            fill: "#000",
            stroke: "#fff",
            cursor: "s-resize"
        }).hide().drag(function(dx, dy) {
            boxNodeDragOnMove(dx, dy, "b")
        },
        function() {
            changeBoxNodePos(this.attr("x") + sideLen / 2, this.attr("y") + sideLen / 2, "b")
        },
        function() {});
        //边界框的右下的点
        rectBoxNodes.rb = paper.rect(0, 0, sideLen, sideLen).attr({
            fill: "#000",
            stroke: "#fff",
            cursor: "se-resize"
        }).hide().drag(function(dx, dy) {
            boxNodeDragOnMove(dx, dy, "rb")
        },
        function() {
            changeBoxNodePos(this.attr("x") + sideLen / 2, this.attr("y") + sideLen / 2, "rb")
        },
        function() {});
        //边界框的右边的点
        rectBoxNodes.r = paper.rect(0, 0, sideLen, sideLen).attr({
            fill: "#000",
            stroke: "#fff",
            cursor: "w-resize"
        }).hide().drag(function(dx, dy) {
            boxNodeDragOnMove(dx, dy, "r")
        },
        function() {
            changeBoxNodePos(this.attr("x") + sideLen / 2, this.attr("y") + sideLen / 2, "r")
        },
        function() {});
        //边界框的右上的点
        rectBoxNodes.rt = paper.rect(0, 0, sideLen, sideLen).attr({
            fill: "#000",
            stroke: "#fff",
            cursor: "ne-resize"
        }).hide().drag(function(dx, dy) {
            boxNodeDragOnMove(dx, dy, "rt")
        },
        function() {
            changeBoxNodePos(this.attr("x") + sideLen / 2, this.attr("y") + sideLen / 2, "rt")
        },
        function() {});
        //边界框的点的拖拽的移动处理函数
        var boxNodeDragOnMove = function(rectPosX, rectPosY, nodeType) {
            if (!a.config.editable) {
                return
            }
            var o = _bx + rectPosX,
            H = _by + rectPosY;
            switch (nodeType) {
            case "t":
                rectBoxInfo.height += rectBoxInfo.y - H;
                rectBoxInfo.y = H;
                break;
            case "lt":
                rectBoxInfo.width += rectBoxInfo.x - o;
                rectBoxInfo.height += rectBoxInfo.y - H;
                rectBoxInfo.x = o;
                rectBoxInfo.y = H;
                break;
            case "l":
                rectBoxInfo.width += rectBoxInfo.x - o;
                rectBoxInfo.x = o;
                break;
            case "lb":
                rectBoxInfo.height = H - rectBoxInfo.y;
                rectBoxInfo.width += rectBoxInfo.x - o;
                rectBoxInfo.x = o;
                break;
            case "b":
                rectBoxInfo.height = H - rectBoxInfo.y;
                break;
            case "rb":
                rectBoxInfo.height = H - rectBoxInfo.y;
                rectBoxInfo.width = o - rectBoxInfo.x;
                break;
            case "r":
                rectBoxInfo.width = o - rectBoxInfo.x;
                break;
            case "rt":
                rectBoxInfo.width = o - rectBoxInfo.x;
                rectBoxInfo.height += rectBoxInfo.y - H;
                rectBoxInfo.y = H;
                break
            }
            parseRect();
        };
        //改变边界框的坐标
        var changeBoxNodePos = function(posX, posY, nodeType) {
            _bx = posX;
            _by = posY;
        };
        //节点绑定点击事件
        b([rectNode.node, rectTextBottom.node, rectTextTop.node, rectImg.node]).bind("click",
        function() {
            if (!a.config.editable) {
                return
            }
            showRectBox();
            var paperMode = b(paper).data("mod");
            b(paper).data("prop","false");
            switch (paperMode) {
            case "pointer":
                break;
            case "path"://路径模式
                var r = b(paper).data("currNode");
                //如果之前点击的节点和现在的节点不是同一个节点，添加两节点之间的路径
                if (r && r.getId() != rectId && r.getId().substring(0, 4) == "rect") {
                    b(paper).trigger("addpath", [r, rectObj])
                }
                break
            }
            b(paper).trigger("click", rectObj);
            b(paper).data("currNode", rectObj);
            return false
        });
        //节点的点击处理
        var clickRect = function(event, node) {
            if (!a.config.editable) {
                return
            }
            if (node.getId() == rectId) {
                b(paper).trigger("showprops", [rectCfg.props, node])
            } else {
                hideRectBox();
            }
        };
        b(paper).bind("click", clickRect);
        //改变节点的名称
        var changeRectText = function(event, rectText, node) {
            if (node.getId() == rectId) {
                rectTextBottom.attr({
                    text: rectText
                })
            }
        };
        b(paper).bind("textchange", changeRectText);
        //生成节点的边框路径
        function genRectBoxPath() {
            return "M" + rectBoxInfo.x + " " + rectBoxInfo.y + "L" + rectBoxInfo.x + " " + (rectBoxInfo.y + rectBoxInfo.height) + "L" + (rectBoxInfo.x + rectBoxInfo.width) + " " + (rectBoxInfo.y + rectBoxInfo.height) + "L" + (rectBoxInfo.x + rectBoxInfo.width) + " " + rectBoxInfo.y + "L" + rectBoxInfo.x + " " + rectBoxInfo.y
        }
        //边界框的展示
        function showRectBox() {
            rectBoxPath.show();
            for (var o in rectBoxNodes) {
                rectBoxNodes[o].show()
            }
        }
        //边界框的隐藏
        function hideRectBox() {
            rectBoxPath.hide();
            for (var o in rectBoxNodes) {
                rectBoxNodes[o].hide()
            }
        }
        //解析节点信息
        function parseRect() {
            var rectPosX = rectBoxInfo.x + rectCfg.margin,
            rectPosY = rectBoxInfo.y + rectCfg.margin,
            rectWidth = rectBoxInfo.width - rectCfg.margin * 2,
            rectHeight = rectBoxInfo.height - rectCfg.margin * 2;
            rectNode.attr({
                x: rectPosX,
                y: rectPosY,
                width: rectWidth,
                height: rectHeight
            });
            switch (rectCfg.showType) {
            case "image":
                rectImg.attr({
                    x:
                    rectPosX + (rectWidth - rectCfg.img.width) / 2,
                    y: rectPosY + (rectHeight - rectCfg.img.height) / 2
                }).show();
                break;
            case "text":
                rectNode.show();
                rectTextBottom.attr({
                    x:
                    rectPosX + rectWidth / 2,
                    y: rectPosY + rectHeight / 2
                }).show();
                break;
            case "image&text":
                rectNode.show();
                rectTextTop.attr({
                    x:
                    rectPosX + rectCfg.img.width + (rectWidth - rectCfg.img.width) / 2,
                    y: rectPosY + a.config.lineHeight / 2
                }).show();
                rectTextBottom.attr({
                    x: rectPosX + rectCfg.img.width + (rectWidth - rectCfg.img.width) / 2,
                    y: rectPosY + (rectHeight - a.config.lineHeight) / 2 + a.config.lineHeight
                }).show();
                rectImg.attr({
                    x: rectPosX + rectCfg.img.width / 2,
                    y: rectPosY + (rectHeight - rectCfg.img.height) / 2
                }).show();
            case "node":
                rectNode.show();
                rectTextTop.attr({
                    x:
                    rectPosX + rectCfg.img.width + (rectWidth - rectCfg.img.width) / 2,
                    y: rectPosY + a.config.lineHeight / 2,
                    text:rectCfg.props["nodeId"].value
                }).show();
                rectTextBottom.attr({
                    x: rectPosX + rectCfg.img.width + (rectWidth - rectCfg.img.width) / 2,
                    y: rectPosY + (rectHeight - a.config.lineHeight) / 2 + a.config.lineHeight
                }).show();
                /*rectImg.attr({
                    x: rectPosX + rectCfg.img.width / 2,
                    y: rectPosY + (rectHeight - rectCfg.img.height) / 2
                }).show();*/
                break
            }
            rectBoxNodes.t.attr({
                x: rectBoxInfo.x + rectBoxInfo.width / 2 - sideLen / 2,
                y: rectBoxInfo.y - sideLen / 2
            });
            rectBoxNodes.lt.attr({
                x: rectBoxInfo.x - sideLen / 2,
                y: rectBoxInfo.y - sideLen / 2
            });
            rectBoxNodes.l.attr({
                x: rectBoxInfo.x - sideLen / 2,
                y: rectBoxInfo.y - sideLen / 2 + rectBoxInfo.height / 2
            });
            rectBoxNodes.lb.attr({
                x: rectBoxInfo.x - sideLen / 2,
                y: rectBoxInfo.y - sideLen / 2 + rectBoxInfo.height
            });
            rectBoxNodes.b.attr({
                x: rectBoxInfo.x - sideLen / 2 + rectBoxInfo.width / 2,
                y: rectBoxInfo.y - sideLen / 2 + rectBoxInfo.height
            });
            rectBoxNodes.rb.attr({
                x: rectBoxInfo.x - sideLen / 2 + rectBoxInfo.width,
                y: rectBoxInfo.y - sideLen / 2 + rectBoxInfo.height
            });
            rectBoxNodes.r.attr({
                x: rectBoxInfo.x - sideLen / 2 + rectBoxInfo.width,
                y: rectBoxInfo.y - sideLen / 2 + rectBoxInfo.height / 2
            });
            rectBoxNodes.rt.attr({
                x: rectBoxInfo.x - sideLen / 2 + rectBoxInfo.width,
                y: rectBoxInfo.y - sideLen / 2
            });
            rectBoxPath.attr({
                path: genRectBoxPath()
            });
            b(paper).trigger("rectresize", rectObj)
        }
        //把节点的信息装换成JSON字符串
        this.toJson = function() {
            var r = "{\"type\":\"" + rectCfg.type + "\",\"text\":{\"text\":\"" + rectTextBottom.attr("text") + "\"}, \"attr\":{ \"x\":" + Math.round(rectNode.attr("x")) + ", \"y\":" + Math.round(rectNode.attr("y")) + ", \"width\":" + Math.round(rectNode.attr("width")) + ", \"height\":" + Math.round(rectNode.attr("height")) + "}, \"props\":{";
            for (var o in rectCfg.props) {
            	if(!b.isFunction(rectCfg.props[o])){
                    r += "\""+ o + "\":{\"value\":\"" + rectCfg.props[o].value.replace(new RegExp("\\\\","g"),"\\\\").replace(new RegExp("\t","g"),"\\t").replace(new RegExp("\r","g"),"\\r").replace(new RegExp("\n","g"),"\\n").replace(new RegExp("\"","g"),"\\\"") + "\"},";
            	}
            }
            if (r.substring(r.length - 1, r.length) == ",") {
                r = r.substring(0, r.length - 1)
            }
            r += "}}";
            return r
        };
        //装载节点的信息
        this.restore = function(cfg) {
            var restoreCfg = cfg;
            rectCfg = b.extend(true, rectCfg, cfg);
            rectTextBottom.attr({
                text: restoreCfg.text.text
            });
            parseRect();
        };
        //获取节点的边界框的信息
        this.getBBox = function() {
            return rectBoxInfo
        };
        //获取节点的ID
        this.getId = function() {
            return rectId;
        };
        //节点的删除
        this.remove = function() {
            rectNode.remove();
            rectTextBottom.remove();
            rectTextTop.remove();
            rectImg.remove();
            rectBoxPath.remove();
            for (var o in rectBoxNodes) {
                rectBoxNodes[o].remove()
            }
        };
        //获取节点的名称
        this.text = function() {
            return rectTextBottom.attr("text");
        };
        //添加节点的属性
        this.attr = function(rectAttr) {
            if (rectAttr) {
                rectNode.attr(rectAttr);
            }
        };
        parseRect();
    };
    //路径
    a.path = function(_cfg, _paper, _fromNode, _toNode) {
        var pathObj = this,
        paper = _paper,
        pathCfg = b.extend(true, {},
        a.config.path),
        elePath,
        eleArrow,
        elePathText,
        pathTextPos = pathCfg.textPos,
        pathTextPosX,
        pathTextPosY,
        fromNode = _fromNode,
        toNode = _toNode,
        pathId = "path" + a.util.nextId(),
        pathDots;
        function pathPoint(_pointType, _dotPos, _leftDot, _rightDot) {
            var pathPointObj = this,
            pointType = _pointType,
            pathDot,
            leftDot = _leftDot,
            rightDot = _rightDot,
            dotDragStartX,
            dotDragStartY,
            dotPos = _dotPos;
            switch (pointType) {
            case "from"://路径开始位置的显示的点
                pathDot = paper.rect(_dotPos.x - pathCfg.attr.fromDot.width / 2, _dotPos.y - pathCfg.attr.fromDot.height / 2, pathCfg.attr.fromDot.width, pathCfg.attr.fromDot.height).attr(pathCfg.attr.fromDot);
                break;
            case "big"://路径中间位置可拖拽的转折点
                pathDot = paper.rect(_dotPos.x - pathCfg.attr.bigDot.width / 2, _dotPos.y - pathCfg.attr.bigDot.height / 2, pathCfg.attr.bigDot.width, pathCfg.attr.bigDot.height).attr(pathCfg.attr.bigDot);
                break;
            case "small"://路径中间位置可拖拽的中间点
                pathDot = paper.rect(_dotPos.x - pathCfg.attr.smallDot.width / 2, _dotPos.y - pathCfg.attr.smallDot.height / 2, pathCfg.attr.smallDot.width, pathCfg.attr.smallDot.height).attr(pathCfg.attr.smallDot);
                break;
            case "to"://路径结束位置的显示的点
                pathDot = paper.rect(_dotPos.x - pathCfg.attr.toDot.width / 2, _dotPos.y - pathCfg.attr.toDot.height / 2, pathCfg.attr.toDot.width, pathCfg.attr.toDot.height).attr(pathCfg.attr.toDot);
                break
            }
            //只有路径的中间的点可拖拽
            if (pathDot && (pointType == "big" || pointType == "small")) {
                pathDot.drag(function(dx, dy) {
                    pathDotDragOnMove(dx, dy);
                },
                function() {
                    pathDotDragOnStart();
                },
                function() {
                    pathDotDragOnEnd();
                });
                //路径可拖拽点的移动处理函数
                var pathDotDragOnMove = function(dx, dy) {
                    var dotPosX = (dotDragStartX + dx),
                    dotPosY = (dotDragStartY + dy);
                    pathPointObj.moveTo(dotPosX, dotPosY);
               };
                //路径可拖拽点的拖拽开始的处理函数
                var pathDotDragOnStart = function() {
                    if (pointType == "big") {
                        dotDragStartX = pathDot.attr("x") + pathCfg.attr.bigDot.width / 2;
                        dotDragStartY = pathDot.attr("y") + pathCfg.attr.bigDot.height / 2
                    }
                    if (pointType == "small") {
                        dotDragStartX = pathDot.attr("x") + pathCfg.attr.smallDot.width / 2;
                        dotDragStartY = pathDot.attr("y") + pathCfg.attr.smallDot.height / 2
                    }
                };
                //路径可拖拽点的拖拽结束处理函数
                var pathDotDragOnEnd = function() {
                    b(paper).trigger("click", pathObj);
                    b(paper).data("currNode", pathObj);
                    b(paper).data("prop","false");
                	b(paper).data("pathDotDrag", "true");
                }
            }
            //如果传入参数则设置路径可拖拽点的类型，没有传入参数则获取路径可拖拽点的类型
            this.type = function(_type) {
                if (_type) {
                    pointType = _type;
                } else {
                    return pointType;
                }
            };
             //如果传入参数则设置路径可拖拽点，没有传入参数则获取路径可拖拽点
            this.node = function(_dot) {
                if (_dot) {
                    pathDot = _dot;
                } else {
                    return pathDot;
                }
            };
            //如果传入参数则设置路径可拖拽点的左边的点，没有传入参数则获取路径可拖拽点的左边的点
            this.left = function(_dot) {
                if (_dot) {
                    leftDot = _dot;
                } else {
                    return leftDot;
                }
            };
            //如果传入参数则设置路径可拖拽点的右边的点，没有传入参数则获取路径可拖拽点的右边的点
            this.right = function(_dot) {
                if (_dot) {
                    rightDot = _dot;
                } else {
                    return rightDot;
                }
            };
            //路径可拖拽点的删除
            this.remove = function() {
                leftDot = null;
                rightDot = null;
                pathDot.remove()
            };
             //如果传入参数则设置路径可拖拽点的坐标，没有传入参数则获取路径可拖拽点的坐标
            this.pos = function(_pos) {
                if (_pos) {
                    dotPos = _pos;
                    pathDot.attr({
                        x: dotPos.x - pathDot.attr("width") / 2,
                        y: dotPos.y - pathDot.attr("height") / 2
                    });
                    return this
                } else {
                    return dotPos
                }
            };
            //路径的可拖拽点的移动处理
            this.moveTo = function(_posX, _posY) {
                this.pos({
                    x: _posX,
                    y: _posY
                });
                switch (pointType) {
                case "from":
                    if (rightDot && rightDot.right() && rightDot.right().type() == "to") {
                        rightDot.right().pos(a.util.connPoint(toNode.getBBox(), dotPos))
                    }
                    if (rightDot && rightDot.right()) {
                        rightDot.pos(a.util.center(dotPos, rightDot.right().pos()))
                    }
                    break;
                case "big":
                    if (rightDot && rightDot.right() && rightDot.right().type() == "to") {
                        rightDot.right().pos(a.util.connPoint(toNode.getBBox(), dotPos))
                    }
                    if (leftDot && leftDot.left() && leftDot.left().type() == "from") {
                        leftDot.left().pos(a.util.connPoint(fromNode.getBBox(), dotPos))
                    }
                    if (rightDot && rightDot.right()) {
                        rightDot.pos(a.util.center(dotPos, rightDot.right().pos()))
                    }
                    if (leftDot && leftDot.left()) {
                        leftDot.pos(a.util.center(dotPos, leftDot.left().pos()))
                    }
                    var S = {
                        x: dotPos.x,
                        y: dotPos.y
                    };
                    if (a.util.isLine(leftDot.left().pos(), S, rightDot.right().pos())) {
                        pointType = "small";
                        pathDot.attr(pathCfg.attr.smallDot);
                        this.pos(S);
                        var P = leftDot;
                        leftDot.left().right(leftDot.right());
                        leftDot = leftDot.left();
                        P.remove();
                        var R = rightDot;
                        rightDot.right().left(rightDot.left());
                        rightDot = rightDot.right();
                        R.remove()
                    }
                    break;
                case "small":
                    if (leftDot && rightDot && !a.util.isLine(leftDot.pos(), {
                        x: dotPos.x,
                        y: dotPos.y
                    },
                    rightDot.pos())) {
                        pointType = "big";
                        pathDot.attr(pathCfg.attr.bigDot);
                        var P = new pathPoint("small", a.util.center(leftDot.pos(), dotPos), leftDot, leftDot.right());
                        leftDot.right(P);
                        leftDot = P;
                        var R = new pathPoint("small", a.util.center(rightDot.pos(), dotPos), rightDot.left(), rightDot);
                        rightDot.left(R);
                        rightDot = R
                    }
                    break;
                case "to":
                    if (leftDot && leftDot.left() && leftDot.left().type() == "from") {
                        leftDot.left().pos(a.util.connPoint(fromNode.getBBox(), dotPos))
                    }
                    if (leftDot && leftDot.left()) {
                        leftDot.pos(a.util.center(dotPos, leftDot.left().pos()))
                    }
                    break
                }
                dots2Path()
            }
        }
        //解析路径的各个点
        function parsePathDots() {
            var fromPathDot,
            toPathDot,
            fromNodeBox = fromNode.getBBox(),
            toNodeBox = toNode.getBBox(),
            fromDotPos,
            toDotPos;
            fromDotPos = a.util.connPoint(fromNodeBox, {
                x: toNodeBox.x + toNodeBox.width / 2,
                y: toNodeBox.y + toNodeBox.height / 2
            });
            toDotPos = a.util.connPoint(toNodeBox, fromDotPos);
            fromPathDot = new pathPoint("from", fromDotPos, null, new pathPoint("small", {
                x: (fromDotPos.x + toDotPos.x) / 2,
                y: (fromDotPos.y + toDotPos.y) / 2
            }));
            fromPathDot.right().left(fromPathDot);
            toPathDot = new pathPoint("to", toDotPos, fromPathDot.right(), null);
            fromPathDot.right().right(toPathDot);
            //把路径的点坐标转换成路径字符串
            this.toPathString = function() {
                if (!fromPathDot) {
                    return ""
                }
                var fromDot = fromPathDot,
                pathStr = "M" + fromDot.pos().x + " " + fromDot.pos().y,
                arrowStr = "";
                while (fromDot.right()) {
                    fromDot = fromDot.right();
                    pathStr += "L" + fromDot.pos().x + " " + fromDot.pos().y
                }
                var arrowPos = a.util.arrow(fromDot.left().pos(), fromDot.pos(), pathCfg.attr.arrow.radius);
                arrowStr = "M" + arrowPos[0].x + " " + arrowPos[0].y + "L" + arrowPos[1].x + " " + arrowPos[1].y + "L" + arrowPos[2].x + " " + arrowPos[2].y + "z";
                return [pathStr, arrowStr]
            };
            //把路径的点转换成JSON串
            this.toJson = function() {
                var dotsJson = "[",
                rDot = fromPathDot;
                while (rDot) {
                    if (rDot.type() == "big") {
                        dotsJson += "{\"x\":" + Math.round(rDot.pos().x) + ",\"y\":" + Math.round(rDot.pos().y) + "},"
                    }
                    rDot = rDot.right()
                }
                if (dotsJson.substring(dotsJson.length - 1, dotsJson.length) == ",") {
                    dotsJson = dotsJson.substring(0, dotsJson.length - 1)
                }
                dotsJson += "]";
                return dotsJson
            };
            //转载路径信息
            this.restore = function(_dots) {
                var dots = _dots,
                rDot = fromPathDot.right();
                for (var idx = 0; idx < dots.length; idx++) {
                    rDot.moveTo(dots[idx].x, dots[idx].y);
                    rDot.moveTo(dots[idx].x, dots[idx].y);
                    rDot = rDot.right()
                }
                this.hide()
            };
            //获取路径的开始的点
            this.fromDot = function() {
                return fromPathDot
            };
            //获取路径的结束的点
            this.toDot = function() {
                return toPathDot
            };
            //获取路径的中间的点
            this.midDot = function() {
                var mDot = fromPathDot.right(),
                rrDot = fromPathDot.right().right();
                while (rrDot.right() && rrDot.right().right()) {
                    rrDot = rrDot.right().right();
                    mDot = mDot.right()
                }
                return mDot
            };
            //路径的点的展示
            this.show = function() {
                var fromDot = fromPathDot;
                while (fromDot) {
                    fromDot.node().show();
                    fromDot = fromDot.right()
                }
            };
            //路径的点的隐藏
            this.hide = function() {
                var fromDot = fromPathDot;
                while (fromDot) {
                    fromDot.node().hide();
                    fromDot = fromDot.right()
                }
            };
            //路径的点的删除
            this.remove = function() {
                var fromDot = fromPathDot;
                while (fromDot) {
                    if (fromDot.right()) {
                        fromDot = fromDot.right();
                        fromDot.left().remove()
                    } else {
                        fromDot.remove();
                        fromDot = null
                    }
                }
            }
        }
        pathCfg = b.extend(true, pathCfg, _cfg);
        elePath = paper.path(pathCfg.attr.path.path).attr(pathCfg.attr.path);
        eleArrow = paper.path(pathCfg.attr.arrow.path).attr(pathCfg.attr.arrow);
        pathDots = new parsePathDots();
        pathDots.hide();
        elePathText = paper.text(0, 0, pathCfg.text.text).attr(pathCfg.text).attr({
            text: pathCfg.text.text.replace("{from}", fromNode.text()).replace("{to}", toNode.text())
        });
        //路径上面的文字的拖拽处理
        elePathText.drag(function(dx, dy) {
            if (!a.config.editable) {
                return
            }
            elePathText.attr({
                x: pathTextPosX + dx,
                y: pathTextPosY + dy
            })
        },
        function() {
            pathTextPosX = elePathText.attr("x");
            pathTextPosY = elePathText.attr("y")
        },
        function() {
            var midDotPos = pathDots.midDot().pos();
            pathTextPos = {
                x: elePathText.attr("x") - midDotPos.x,
                y: elePathText.attr("y") - midDotPos.y
            }
        });
        dots2Path();
        //绑定路径的点击事件
        b([elePath.node, eleArrow.node, elePathText.node]).bind("click",
        function() {
            if (!a.config.editable) {
                return
            }
            b(paper).trigger("click", pathObj);
            b(paper).data("currNode", pathObj);
            b(paper).data("prop","false");
            return false
        });
        //路径的点击处理
        var clickPath = function(event, activePathObj) {
            if (!a.config.editable) {
                return
            }
            if (activePathObj && activePathObj.getId() == pathId) {
                pathDots.show();
                b(paper).trigger("showprops", [pathCfg.props, pathObj])
            } else {
                pathDots.hide();
            }
            var paperMode = b(paper).data("mod");
            switch (paperMode) {
            case "pointer":
                break;
            case "path":
                break
            }
        };
        b(paper).bind("click", clickPath);
        //删除节点的操作
        var removeRect = function(event, node) {
            if (!a.config.editable) {
                return
            }
            if (node && (node.getId() == fromNode.getId() || node.getId() == toNode.getId())) {
                b(paper).trigger("removepath", pathObj);
            }
        };
        b(paper).bind("removerect", removeRect);
        //改变节点的大小的处理
        var resizeRect = function(event, rectObj) {
            if (!a.config.editable) {
                return
            }
            //是路径的开始的点
            if (fromNode && fromNode.getId() == rectObj.getId()) {
                var dotPos;
                if (pathDots.fromDot().right().right().type() == "to") {
                    dotPos = {
                        x: toNode.getBBox().x + toNode.getBBox().width / 2,
                        y: toNode.getBBox().y + toNode.getBBox().height / 2
                    }
                } else {
                    dotPos = pathDots.fromDot().right().right().pos()
                }
                var moveToPos = a.util.connPoint(fromNode.getBBox(), dotPos);
                pathDots.fromDot().moveTo(moveToPos.x, moveToPos.y);
                dots2Path();
            }
            //是路径的结束的点
            if (toNode && toNode.getId() == rectObj.getId()) {
                var dotPos;
                if (pathDots.toDot().left().left().type() == "from") {
                    dotPos = {
                        x: fromNode.getBBox().x + fromNode.getBBox().width / 2,
                        y: fromNode.getBBox().y + fromNode.getBBox().height / 2
                    }
                } else {
                    dotPos = pathDots.toDot().left().left().pos()
                }
                var moveToPos = a.util.connPoint(toNode.getBBox(), dotPos);
                pathDots.toDot().moveTo(moveToPos.x, moveToPos.y);
                dots2Path();
            }
        };
        b(paper).bind("rectresize", resizeRect);
        //改变路径名称的方法
        var changePathText = function(event, pathText, node) {
            if (node.getId() == pathId) {
                elePathText.attr({
                    text: pathText
                })
            }
        };
        b(paper).bind("textchange", changePathText);
        //获取开始的节点
        this.from = function() {
            return fromNode
        };
        //获取结束的节点
        this.to = function() {
            return toNode
        };
        //转换成Json串
        this.toJson = function() {
            var pathJson = "{\"from\":\"" + fromNode.getId() + "\",\"to\":\"" + toNode.getId() + "\", \"dots\":" + pathDots.toJson() + ",\"text\":{\"text\":\"" + elePathText.attr("text") + "\"},\"textPos\":{\"x\":" + Math.round(pathTextPos.x) + ",\"y\":" + Math.round(pathTextPos.y) + "}, \"props\":{";
            for (var o in pathCfg.props) {
            	if(!b.isFunction(pathCfg.props[o])){
                    pathJson += "\"" + o + "\":{\"value\":\"" + pathCfg.props[o].value.replace(new RegExp("\\\\","g"),"\\\\").replace(new RegExp("\t","g"),"\\t").replace(new RegExp("\r","g"),"\\r").replace(new RegExp("\n","g"),"\\n").replace(new RegExp("\"","g"),"\\\"") + "\"},"
            	}
            }
            if (pathJson.substring(pathJson.length - 1, pathJson.length) == ",") {
                pathJson = pathJson.substring(0, pathJson.length - 1)
            }
            pathJson += "}}";
            return pathJson
        };
        //装载数据
        this.restore = function(cfg) {
            var restoreCfg = cfg;
            pathCfg = b.extend(true, pathCfg, cfg);
            pathDots.restore(restoreCfg.dots)
        };
        //路径的删除
        this.remove = function() {
            pathDots.remove();
            elePath.remove();
            eleArrow.remove();
            elePathText.remove();
            try {
                b(paper).unbind("click", clickPath)
            } catch(o) {}
            try {
                b(paper).unbind("removerect", removeRect);
            } catch(o) {}
            try {
                b(paper).unbind("rectresize", resizeRect)
            } catch(o) {}
            try {
                b(paper).unbind("textchange", changePathText)
            } catch(o) {}
        };
        //把路径上的点转换成路径
        function dots2Path() {
            var dotsPathStr = pathDots.toPathString(),
            mDotPos = pathDots.midDot().pos();
            elePath.attr({
                path: dotsPathStr[0]
            });
            eleArrow.attr({
                path: dotsPathStr[1]
            });
            elePathText.attr({
                x: mDotPos.x + pathTextPos.x,
                y: mDotPos.y + pathTextPos.y
            })
        }
        //获取路径的ID
        this.getId = function() {
            return pathId
        };
        //获取路径的名称
        this.text = function() {
            return elePathText.attr("text")
        };
        //设置路径的属性
        this.attr = function(pathAttr) {
            if (pathAttr && pathAttr.path) {
                elePath.attr(pathAttr.path)
            }
            if (pathAttr && pathAttr.arrow) {
                eleArrow.attr(pathAttr.arrow)
            }
        }
    };
    //属性设计框
    a.props = function(config, paper) {
        var propsObj = this,
        propsArea = b("#workFlow_props").hide().draggable({
            handle: "#workFlow_props_handle"
        }).resizable().css(a.config.props.attr).bind("click",
        function() {
   	        b(_paper).data("prop","true");
            return false;
        }),
        propsTbl = propsArea.find("table"),
        _paper = paper,
        oldNode;
       //展示属性设计框
       var showProps = function(n, _cfgProps, _activeNode) {
            if (oldNode && oldNode.getId() == _activeNode.getId()) {
                return
            }
            oldNode = _activeNode;
            b(propsTbl).find(".editor").each(function() {
            		//如果之前有展示过属性设计框，把之前的属性设计框销毁
                var propEditor = b(this).data("editor");
                if (propEditor) {
                    propEditor.destroy()
                }
            });
            propsTbl.empty();
            propsArea.show();
            //根据属性配置初始化属性设计框的内容
            for (var propsKey in _cfgProps) {
            	if(b.isFunction(_cfgProps[propsKey])){
            	}else{
                    propsTbl.append("<tr><th>" + _cfgProps[propsKey].label + '</th><td><div id="p' + propsKey + '" class="editor"></div></td></tr>');
                    if (_cfgProps[propsKey].editor) {
                        _cfgProps[propsKey].editor().init(_cfgProps, propsKey, "p" + propsKey, _activeNode, _paper)
                    }
            	}
            }
        };
        b(_paper).bind("showprops", showProps)
    };
    //属性的设计框
    a.editors = {
        textEditor: function() {
            var _props, e, _div, _node, _paper;
            this.init = function(props, key, div, node, paper) {
                _props = props;
                _key = key;
                _div = div;
                _node = node;
                _paper = paper;
                b('<input  style="width:100%;"/>').val(_node.text()).change(function() {
                    _props[_key].value = b(this).val();
                    b(_paper).trigger("textchange", [b(this).val(), _node]);
                }).appendTo("#" + _div);
                //存储正在编辑的属性设计框的对象
                b("#" + _div).data("editor", this);
            };
            //设计框销毁的时候，保存属性值
            this.destroy = function() {
                b("#" + _div + " input").each(function() {
                    _props[_key].value = b(this).val();
                    b(_paper).trigger("textchange", [b(this).val(), _node])
                })
            }
        }
    };
    //初始化函数，传入容器的ID和设计器的相关配置属性
    a.init = function(_container, _config) {
        var raphaelWidth = b(window).width();
        var raphaelHeight = b(window).height();
        //绘制画布
        var paper = Raphael(_container, raphaelWidth * 1.5, raphaelHeight * 2);
        //节点集
        var rects = {};
        //路径集
        var paths = {};
        //合并默认配置和自定义配置
        b.extend(true, a.config, _config);
        b(document).keydown(function(event) {
        		//非编辑状态
            if (!a.config.editable) {
                return
            }
            //删除按钮
            if (event.keyCode == 46) {
            		//如果选中的是属性编辑框，不删除节点
            		if(b(paper).data("prop") == "true"){
	            		return;
            		}
            		//获取当前激活的元素
                var currNode = b(paper).data("currNode");
                if (currNode) {
                	//开始和结束节点不删除
                	if(currNode.getId() == "rect0000" || currNode.getId() == "rect9999"){
                		return;
                	};
                    if (currNode.getId().substring(0, 4) == "rect") {//是节点
                        b(paper).trigger("removerect", currNode)
                    } else {
                        if (currNode.getId().substring(0, 4) == "path") {//是路径
                            b(paper).trigger("removepath", currNode)
                        }
                    }
                    b(paper).removeData("currNode");//删除激活的元素信息
										b("#workFlow_props").hide();//属性设计框的隐藏   
                }
            }
        });
        //画布的点击
        b(document).click(function() {
        	if(b(paper).data("pathDotDrag")=="true"){
        		b(paper).data("pathDotDrag","false");
        		return;
        	}
        		//清空激活的当前节点
            b(paper).data("currNode", null);
            b(paper).trigger("click", {
                getId: function() {
                    return "00000000"
                }
            });
            //展示属性设计框
            b(paper).trigger("showprops", [a.config.props.props, {
                getId: function() {
                    return "00000000"
                }
            }])
        });
        //元素删除处理
        var removeNode = function(event, node) {
        		//非编辑状态
            if (!a.config.editable) {
                return;
            }
            if (node.getId().substring(0, 4) == "rect") {//是节点
                rects[node.getId()] = null;
                node.remove();
            } else {
                if (node.getId().substring(0, 4) == "path") {//是路径
                    paths[node.getId()] = null;
                    node.remove();
                }
            }
        };
        //绑定删除路径方法
        b(paper).bind("removepath", removeNode);
        //绑定删除节点方法
        b(paper).bind("removerect", removeNode);
        //绑定增加节点方法
        b(paper).bind("addrect",
        function(event, typ, conf) {
        		//生成一个节点
            var rect = new a.rect(b.extend(true, {},
            a.config.tools.states[typ], conf), paper);
            //把节点数据保存到节点集里面
            rects[rect.getId()] = rect;
        });
        //增加路径方法
        var addPath = function(event, fromNode, toNode) {
        		//生成一条路径
            var path = new a.path({},
            paper, fromNode, toNode);
            //把路径数据保存到路径集里面
            paths[path.getId()] = path;
        };
        //绑定增加路径方法
        b(paper).bind("addpath", addPath);
        //保存模式为选择模式(非连线模式)
        b(paper).data("mod", "point");
        //可编辑状态
        if (a.config.editable) {
        		//工具集的可拖拽
            b("#workFlow_tools").draggable({
                handle: "#workFlow_tools_handle"
            }).css(a.config.tools.attr);
            b("#workFlow_tools .node").hover(function() {
                b(this).addClass("mover")
            },
            function() {
                b(this).removeClass("mover")
            });
            //工具集的可选择节点的点击处理
            b("#workFlow_tools .selectable").click(function() {
                b(".selected").removeClass("selected");
                b(this).addClass("selected");
                b(paper).data("mod", this.id)
            });
            var dropClientX = 0;
            var dropClientY = 0;
            //工具集可拖拽节点的拖拽事件
            b("#workFlow_tools .state").each(function() {
                b(this).draggable({
                	revert:true,
                	proxy: "clone",
                  onDrag:function(e){
                    	dropClientX = e.clientX;
                    	dropClientY = e.clientY;
                    }
                })
            });
            //容器设为可释放的
            b(_container).droppable({
                accept: ".state",
                onDrop: function(e, source) {
                    b(paper).trigger("addrect", [b(source).attr("type"), {
                        attr: {
                            x: dropClientX,
                            y: dropClientY
                        }
                    }])
                }
            });
            //工具集的保存按钮的点击事件
            b("#workFlow_save").click(function() {
                var i = "{\"states\":{";
                for (var c in rects) {
                    if (rects[c]) {
                        i += "\"" + rects[c].getId() + "\":" + rects[c].toJson() + ","
                    }
                }
                if (i.substring(i.length - 1, i.length) == ",") {
                    i = i.substring(0, i.length - 1)
                }
                i += "},\"paths\":{";
                for (var c in paths) {
                    if (paths[c]) {
                        i += "\"" + paths[c].getId() + "\":" + paths[c].toJson() + ","
                    }
                }
                if (i.substring(i.length - 1, i.length) == ",") {
                    i = i.substring(0, i.length - 1)
                }
                i += "},\"props\":{\"props\":{";
                for (var c in a.config.props.props) {
                	if(!b.isFunction(a.config.props.props[c])){
                        i += "\"" + c + "\":{\"value\":\"" + a.config.props.props[c].value + "\"},"
                	}
                }
                if (i.substring(i.length - 1, i.length) == ",") {
                    i = i.substring(0, i.length - 1)
                }
                i += "}}}";
                a.config.tools.save.onclick(i);
            });
            //初始化属性框
            new a.props({},paper);
        }
        //装载数据
        if (_config.restore) {
            var restore = _config.restore;
            var restRects = {};
            //装载节点
            if (restore.states) {
                for (var state in restore.states) {
                    var newRect = new a.rect(b.extend(true, {},
                    a.config.tools.states[restore.states[state].type], restore.states[state]), paper);
                    newRect.restore(restore.states[state]);
                    restRects[state] = newRect;
                    rects[newRect.getId()] = newRect;
                }
            }
            //装载路径
            if (restore.paths) {
                for (var path in restore.paths) {
                    var newPath = new a.path(b.extend(true, {},
                    a.config.tools.path, restore.paths[path]), paper, restRects[restore.paths[path].from], restRects[restore.paths[path].to]);
                    newPath.restore(restore.paths[path]);
                    paths[newPath.getId()] = newPath;
                }
            }
        }
        var historyRects = a.config.historyRects;
        var activeRects = a.config.activeRects;
        var endRects = a.config.endRects;
        if (historyRects.rects.length || activeRects.rects.length) {
            var m = {},
            rectsPathInfo = {};
            var getRectPropId = function(rect){
            	return rect.getId().substring(4);
            };
           //按照节点别封装路径信息
            for (var h in paths) {
                if (!rectsPathInfo[getRectPropId(paths[h].from())]) {
                    rectsPathInfo[getRectPropId(paths[h].from())] = {
                        rect: paths[h].from(),
                        paths: {}
                    }
                }
                rectsPathInfo[getRectPropId(paths[h].from())].paths[paths[h].text()] = paths[h];
                if (!rectsPathInfo[getRectPropId(paths[h].to())]) {
                    rectsPathInfo[getRectPropId(paths[h].to())] = {
                        rect: paths[h].to(),
                        paths: {}
                    }
                }
            }
            //设置历史节点信息
            for (var u = 0; u < historyRects.rects.length; u++) {
                if (rectsPathInfo[historyRects.rects[u].id]) {
                    rectsPathInfo[historyRects.rects[u].id].rect.attr(historyRects.rectAttr);
                }
                for (var t = 0; t < historyRects.rects[u].paths.length; t++) {
                    if (rectsPathInfo[historyRects.rects[u].id].paths[historyRects.rects[u].paths[t]]) {
                        rectsPathInfo[historyRects.rects[u].id].paths[historyRects.rects[u].paths[t]].attr(historyRects.pathAttr);
                    }
                }
            }
            //设置运行的节点信息
            for (var u = 0; u < activeRects.rects.length; u++) {
                if (rectsPathInfo[activeRects.rects[u].id]) {
                    rectsPathInfo[activeRects.rects[u].id].rect.attr(activeRects.rectAttr);
                }
                for (var t = 0; t < activeRects.rects[u].paths.length; t++) {
                    if (rectsPathInfo[activeRects.rects[u].id].paths[activeRects.rects[u].paths[t]]) {
                        rectsPathInfo[activeRects.rects[u].id].paths[activeRects.rects[u].paths[t]].attr(activeRects.pathAttr);
                    }
                }
            }
            
            for (var u = 0; u < endRects.rects.length; u++) {
                if (rectsPathInfo[endRects.rects[u].id]) {
                    rectsPathInfo[endRects.rects[u].id].rect.attr(endRects.rectAttr);
                }
                for (var t = 0; t < endRects.rects[u].paths.length; t++) {
                    if (rectsPathInfo[endRects.rects[u].id].paths[endRects.rects[u].paths[t]]) {
                        rectsPathInfo[endRects.rects[u].id].paths[endRects.rects[u].paths[t]].attr(endRects.pathAttr);
                    }
                }
            }

        }
    };
    a.setCfgPropsPro = function(prop){
			var _props = a.config.props.props;
			for (var k in _props){
				if(prop[k]){
					_props[k].value = prop[k];
				}
			}
			
    };
    b.fn.workFlow = function(config) {
        this.each(function() {
            a.init(this, config)
        })
        return a;
    };
    b.workFlow = a
})(jQuery);