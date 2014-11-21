<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main"/>
        <style>
                @page {
                   /* size: 6in 4in; */  /* width height */
                   	size: 210mm 297mm;
                 /*   margin: 0.25in; */
                   	margin: 0;
                   	
                }
                @font-face {
		 			src: url(http://localhost:8080/Qcards/fonts/arialuni.ttf);
      				-fs-pdf-font-embed: embed;
      				-fs-pdf-font-encoding: cp1250;
    			}
                body{
                        font-family: "Arial Unicode MS", Arial, sans-serif;
                        margin: 0;
                        padding: 0;
                }
               
				.card-holder {
					border:1px solid #EEE;
					width:65mm;
					height:92mm;
					float:left;
					margin:1mm;
				}
				
				.card-img-box {
					border:1px solid #DDD;
					width:60.5mm;
					height:60.5mm;
					float:left;
					margin:2mm;
					-moz-border-radius: 6px;
				    -webkit-border-radius: 6px;
				    -khtml-border-radius: 6px;
				    border-radius: 44px;
					background:center no-repeat;
					background-size:cover;
					
					
				}
				
				.card-back {
					width:61mm;
					height:88mm;
					float:left;
					margin:2mm;
					-moz-border-radius: 6px;
				    -webkit-border-radius: 6px;
				    -khtml-border-radius: 6px;
				    border-radius: 6px;
					background-color:#FF0000;
					background-image:url(http://localhost/qcards/photo/card-back-bg.png);
					background-repeat:no-repeat;
				}
				
				.card-code-holder {
					width:16mm;
					height:16mm;
					background-color: #FFF;
					margin:0 auto;
					padding:1mm;
					margin-top:35mm;
				}
				
				.card-img-tekst {
					border:1px solid #DDD;
					width:60.5mm;
					height:23mm;
					float:left;
					margin:1mm 2mm;
					-moz-border-radius: 6px;
				    -webkit-border-radius: 6px;
				    -khtml-border-radius: 6px;
				    border-radius: 6px;
					text-align:center;
					color:#999999;
					font-family: "Arial Unicode MS", Arial, sans-serif;
				/*	font-family:"Arial Unicode MS", Tahoma, Geneva, sans-serif; */
					font-size:14px;
					font-weight:bold;
				}
				
				.card-img-tekst span {
					display:block;
					color:#FF0000;
					font-family: "Arial Unicode MS", Arial, sans-serif;
				/*	font-family:Comic Sans MS, cursive; */ 
					font-size:28px;
					font-weight:bold;
					margin:6px 0px;
				}
				
				.card-holder-container{
					width:207.5mm;
					height:293.5mm;
					padding-left: 3.5mm ;
					padding-top: 3.5mm ;
				}
				
       </style>
       
      
       
</head>
<body>
	
	<g:each in="${ (0..<totalnumberofpages) }" var="i">
	   
		<div class="card-holder-container">
			<g:each var="j" in="${(0..<9)}">
				<g:if test="${photocodes[i][j] != null}">
				
					<div class="card-holder">
						<div class="card-img-box" >
					    <img src="http://localhost/qcards/photo/${photocodes[i][j]+'.jpg'}" style="width:100%;height:100%;"/>
					    </div>
					    <div class="card-img-tekst">
						    <div id="dynamicDiv" style="width:220px;" ><span id="dynamicSpan" style="display: inline-block;font-size: 20px;">${itemsname[i][j]}</span></div>
						    <div>${itemsfooter[i][j]}</div>
					    </div>
					</div>
				</g:if>
				
			</g:each>
		</div>
		
		
		<div class="card-holder-container">
			<g:each var="j" in="${(0..<9)}">
				<g:if test="${j>=0 && j<=2 && photocodes[i][2 - j] != null}">
		   			<div class="card-holder">
						<div class="card-back">
					   		<div class="card-code-holder">
		   						<img src="http://localhost/qcards/qrcode/${photocodes[i][2-j]+'.png'}" style="width:16mm;height:16mm"/>
		   					</div>
				    	</div>
					</div>
	   			</g:if>
	   			<g:elseif test="${j>=0 && j<=2 && photocodes[i][2 - j] == null}">
	   				<div class="card-holder">
	   				</div>
	   			</g:elseif>
	   			<g:if test="${j>=3 && j<=5 && photocodes[i][8 - j] != null}">
		   			<div class="card-holder">
						<div class="card-back">
					   		<div class="card-code-holder">
		   						<img src="http://localhost/qcards/qrcode/${photocodes[i][8-j]+'.png'}" style="width:16mm;height:16mm"/>
				   			</div>
				    	</div>
					</div>
	   			</g:if>
	   			<g:elseif test="${j>=3 && j<=5 && photocodes[i][8 - j] == null}">
	   				<div class="card-holder">
	   				</div>
	   			</g:elseif>
	   			<g:if test="${j>=6 && j<=8 && photocodes[i][14 - j] != null}">
		   			<div class="card-holder">
						<div class="card-back">
					   		<div class="card-code-holder">
		   						<img src="http://localhost/qcards/qrcode/${photocodes[i][14-j]+'.png'}" style="width:16mm;height:16mm"/>
		   					</div>
				    	</div>
					</div>
	   			</g:if>
	   			<g:elseif test="${j>=6 && j<=8 && photocodes[i][14 - j] == null}">
	   				<div class="card-holder">
	   				</div>
	   			</g:elseif> 	
					   		
				
				
			</g:each>
		</div>
	
	</g:each>
</body>
</html>