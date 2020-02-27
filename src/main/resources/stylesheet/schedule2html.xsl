<?xml version="1.0" encoding="UTF-8"?>
<!--XSL for Inet ScheduleXSLT. Ask Boris Kryzhanovskyi-->
<!DOCTYPE stylesheet [
	<!ENTITY darr "&#8593;">
	<!-- small n, tilde &uarr;это&#8593 -->
]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java">
	<xsl:output method="html" encoding="utf-8" indent="no"/>
	<xsl:template match="/" xml:space="preserve">
		<xsl:apply-templates/>
	</xsl:template>
	<xsl:template match="metadata" xml:space="preserve">
		<head>
			<meta name="viewport" content="height=device-height"/>
			<link rel="stylesheet" type="text/css" href="css/css.css"/>
			<link rel="stylesheet" href="css/fontello.css"/>
			<link rel="shortcut icon" type="image/x-icon" href="/favicon.ico"/>
			<link rel="icon" type="image/png" href="/favicon.png"/>
			<link rel="apple-touch-icon" href="/clock_60.png"/>
			<title>
				<xsl:call-template name="title"/>
			</title>
			<script>
				<!--Google Analytics-->(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
(i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)})(window,document,'script','//www.google-analytics.com/analytics.js','ga');
ga('create', 'UA-28975455-1', 'auto');
ga('send', 'pageview');
</script>
		</head>
		<span id="title">
			<a href="selection.jsf" style="padding-right: 5px; padding-left:0px;">
				<img class="im" src="img/clock_32.png" title="Главная страница" alt="Логотип"/>
			</a>
			<span style="white-space:nowrap;">
				<xsl:call-template name="title"/>
			</span>
			<span style="white-space:nowrap; color:grey; font-size:smaller"> (<xsl:value-of select="/schedule/metadata/@description"/>)</span>
		</span>
	</xsl:template>
	<xsl:template name="title">Расписание
        <xsl:choose>
			<xsl:when test="@type='group'">на группу </xsl:when>
			<xsl:otherwise>
				<xsl:choose>
					<xsl:when test="@type='teacher'">на преподавателя </xsl:when>
					<xsl:otherwise>на аудиторию </xsl:otherwise>
				</xsl:choose>
			</xsl:otherwise>
		</xsl:choose>
		<xsl:value-of select="/schedule/metadata/@name"/>
	</xsl:template>
	<xsl:template match="schedule-elements" xml:space="preserve">
		<xsl:variable name="uarr" select="'&#8595;'"/>
		<xsl:variable name="todayDate" select="java:format(java:java.text.SimpleDateFormat.new('dd-MM-yyyy'), java:java.util.Date.new())"/>
		<table border="0" width="100%" cellspacing="0">
			<tr>
				<th class="header">
					<!--Empty upper-left table cell-->
					<table>
						<tr>
							<td id="dayName"/>
						</tr>
						<tr>
							<td id="date"/>
						</tr>
					</table>
				</th>
				<xsl:for-each select="/schedule/week/day">
					<xsl:if test="(@day&lt;6) or (/schedule/metadata/@has-weekend-pairs='true')">
						<!--Hide weekend header cells if not applicable-->
						<th class="header">
							<xsl:if test="@date=$todayDate">
								<xsl:attribute name="title">Сегодняшний день</xsl:attribute>
							</xsl:if>
							<table id="header-cell-table">
								<tr>
									<td width="60%" id="dayName">
										<xsl:value-of select="@name"/>
										<xsl:if test="@date=$todayDate">&#160;<img class="im" src="img/clock.gif" title="Сегодняшний день" alt="Сегодня"/>
										</xsl:if>
									</td>
									<td id="date">
										<xsl:value-of select="@display-name"/>
									</td>
								</tr>
							</table>
						</th>
					</xsl:if>
				</xsl:for-each>
			</tr>
			<xsl:if test="/schedule/metadata/@has-0th-pairs='true'">
				<!--Hide 0th pair if not applicable-->
				<tr>
					<td class="pair">0 пара<div id="pair-timing">06:45 - 07:30<br/>07:35 - 08:20</div>
					</td>
					<xsl:call-template name="schedule-row">
						<xsl:with-param name="pair" select="'0'"/>
					</xsl:call-template>
				</tr>
			</xsl:if>
			<tr>
				<td class="pair">1 пара<div id="pair-timing">08:30 - 09:15<br/>09:20 - 10:05</div>
				</td>
				<xsl:call-template name="schedule-row">
					<xsl:with-param name="pair" select="'1'"/>
				</xsl:call-template>
			</tr>
			<tr>
				<td class="pair">2 пара<div id="pair-timing">10:15 - 11:00<br/>11:05 - 11:50</div>
				</td>
				<xsl:call-template name="schedule-row">
					<xsl:with-param name="pair" select="'2'"/>
				</xsl:call-template>
			</tr>
			<tr>
				<td class="pair">3 пара<div id="pair-timing">12:10 - 12:55<br/>13:00 - 13:45</div>
				</td>
				<xsl:call-template name="schedule-row">
					<xsl:with-param name="pair" select="'3'"/>
				</xsl:call-template>
			</tr>
			<tr>
				<td class="pair">4 пара<div id="pair-timing">13:55 - 14:40<br/>14:45 - 15:30</div>
				</td>
				<xsl:call-template name="schedule-row">
					<xsl:with-param name="pair" select="'4'"/>
				</xsl:call-template>
			</tr>
			<tr>
				<td class="pair">5 пара<div id="pair-timing">15:50 - 16:35<br/>16:40 - 17:25</div>
				</td>
				<xsl:call-template name="schedule-row">
					<xsl:with-param name="pair" select="'5'"/>
				</xsl:call-template>
			</tr>
			<xsl:if test="/schedule/metadata/@has-three-last-pairs='true'">
				<tr>
					<td class="pair">6 пара<div id="pair-timing">17:35 - 18:20<br/>18:25 - 19:10</div>
					</td>
					<xsl:call-template name="schedule-row">
						<xsl:with-param name="pair" select="'6'"/>
					</xsl:call-template>
				</tr>
				<xsl:if test="/schedule/metadata/@has-two-last-pairs='true'">
					<tr>
						<td class="pair">7 пара<div id="pair-timing">19:20 - 20:05<br/>20:10 - 20:55</div>
						</td>
						<xsl:call-template name="schedule-row">
							<xsl:with-param name="pair" select="'7'"/>
						</xsl:call-template>
					</tr>
					<xsl:if test="/schedule/metadata/@has-last-pairs='true'">
						<!--Hide 8th pair if not applicable-->
						<tr>
							<td class="pair">8 пара<div id="pair-timing">21:05 - 21:50<br/>21:55 - 22:40</div>
							</td>
							<xsl:call-template name="schedule-row">
								<xsl:with-param name="pair" select="'8'"/>
							</xsl:call-template>
						</tr>
					</xsl:if>
				</xsl:if>
			</xsl:if>
		</table>
	</xsl:template>
	<xsl:template name="schedule-row">
		<xsl:param name="pair"/>
		<xsl:call-template name="schedule-cell">
			<xsl:with-param name="pair" select="$pair"/>
			<xsl:with-param name="day" select="'1'"/>
		</xsl:call-template>
		<xsl:call-template name="schedule-cell">
			<xsl:with-param name="pair" select="$pair"/>
			<xsl:with-param name="day" select="'2'"/>
		</xsl:call-template>
		<xsl:call-template name="schedule-cell">
			<xsl:with-param name="pair" select="$pair"/>
			<xsl:with-param name="day" select="'3'"/>
		</xsl:call-template>
		<xsl:call-template name="schedule-cell">
			<xsl:with-param name="pair" select="$pair"/>
			<xsl:with-param name="day" select="'4'"/>
		</xsl:call-template>
		<xsl:call-template name="schedule-cell">
			<xsl:with-param name="pair" select="$pair"/>
			<xsl:with-param name="day" select="'5'"/>
		</xsl:call-template>
		<xsl:if test="/schedule/metadata/@has-weekend-pairs='true'">
			<xsl:call-template name="schedule-cell">
				<xsl:with-param name="pair" select="$pair"/>
				<xsl:with-param name="day" select="'6'"/>
			</xsl:call-template>
			<xsl:call-template name="schedule-cell">
				<xsl:with-param name="pair" select="$pair"/>
				<xsl:with-param name="day" select="'7'"/>
			</xsl:call-template>
		</xsl:if>
	</xsl:template>
	<xsl:template name="schedule-cell">
		<xsl:param name="pair"/>
		<xsl:param name="day"/>
		<td>
			<xsl:attribute name="id"><xsl:choose><xsl:when test="$day='6' or $day='7'">weekend-cell</xsl:when><xsl:otherwise><xsl:choose><xsl:when test="$pair='0'">zeroth-cell</xsl:when><xsl:otherwise>cell</xsl:otherwise></xsl:choose></xsl:otherwise></xsl:choose></xsl:attribute>
			<xsl:choose>
				<xsl:when test="/schedule/schedule-elements/schedule-element[@pair=$pair and @day=$day]">
					<xsl:for-each select="/schedule/schedule-elements/schedule-element[@pair=$pair and @day=$day]">
						<!--There should be a single element in this for-each loop-->
						<xsl:call-template name="schedule-element"/>
					</xsl:for-each>
				</xsl:when>
				<xsl:otherwise>
					<div id="empty"/>
				</xsl:otherwise>
			</xsl:choose>
		</td>
	</xsl:template>
	<xsl:template match="day"/>
	<xsl:template match="schedule-element" name="schedule-element">
		<xsl:choose>
			<xsl:when test="@contains-multiple">
				<!--This is a cell with multiple elements-->
				<xsl:if test="@contains-mergeable">
					<!--This is a lesson type and subject - mergeable elements-->
					<table id="element-table">
						<tr>
							<td colspan="3">
								<xsl:call-template name="subject-cell">
									<xsl:with-param name="elementNode" select="./schedule-element[1]"/>
								</xsl:call-template>
							</td>
						</tr>
					</table>
				</xsl:if>
				<xsl:for-each select="schedule-element">
					<xsl:call-template name="schedule-element-impl"/>
					<xsl:if test="not(@last)">
						<hr id="multiple-delim"/>
					</xsl:if>
				</xsl:for-each>
			</xsl:when>
			<xsl:otherwise>
				<!--This is a plain cell with a single element-->
				<xsl:call-template name="schedule-element-impl"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template name="schedule-element-impl">
		<table id="element-table">
			<xsl:if test="not(@is-one-of-mergeable)">
				<tr>
					<td colspan="3">
						<xsl:call-template name="subject-cell">
							<xsl:with-param name="elementNode" select="."/>
						</xsl:call-template>
					</td>
				</tr>
			</xsl:if>
			<xsl:choose>
				<xsl:when test="@is-one-of-multiple">
					<!--THIS IS ALL FOR GROUP ONLY!-->
					<tr>
						<tr>
							<td id="room-img">
								<i class="icon-location"/>
							</td>
							<td id="room-small">
								<span style="margin-right: -14px;">
									<!--Euristic number. Allows to strech over lesson-type a bit.-->
									<xsl:choose>
										<xsl:when test="/schedule/@show-links='true'">
											<a id="schedule-link">
												<xsl:attribute name="title">перейти к расписанию на аудиторию
                                                </xsl:attribute>
												<xsl:attribute name="href">schedule?room=<xsl:value-of select="room/@id"/><xsl:call-template name="append-week-param"/></xsl:attribute>
												<xsl:value-of select="room/display-name"/>
											</a>
										</xsl:when>
										<xsl:otherwise>
											<xsl:value-of select="room/display-name"/>
										</xsl:otherwise>
									</xsl:choose>
								</span>
							</td>
							<td colspan="2" align="right">
								<xsl:call-template name="type-cell">
									<xsl:with-param name="elementNode" select="."/>
								</xsl:call-template>
							</td>
						</tr>
						<tr>
							<td id="teacher-img">
								<i class="icon-user"/>
							</td>
							<td id="teacher">
								<a id="schedule-link">
									<xsl:attribute name="title"><xsl:value-of select="teacher/@full-name"/></xsl:attribute>
									<xsl:attribute name="href">schedule?employee=<xsl:value-of select="teacher/@id"/><xsl:call-template name="append-week-param"/></xsl:attribute>
									<xsl:value-of select="teacher/display-name"/>
								</a>
							</td>
							<td nowrap="nowrap" id="subgroup-small">
								<span id="subgroup-img">
									<i class="icon-users"/>
								</span>
								<xsl:for-each select="groups/group">
									<xsl:if test="@main='true'">
										<xsl:if test="not(@has-subgroup-prefix)">
											<span id="group-text">подгр.</span>
										</xsl:if>
										<xsl:call-template name="group"/>
									</xsl:if>
								</xsl:for-each>
							</td>
						</tr>
					</tr>
				</xsl:when>
				<xsl:otherwise>
					<!--Since its a plain element, it cant be @one-of-mergeable, because not @one-of-multiple-->
					<tr>
						<td colspan="2">
							<xsl:call-template name="type-cell">
								<xsl:with-param name="elementNode" select="."/>
							</xsl:call-template>
						</td>
					</tr>
					<xsl:if test="(/schedule/metadata/@type='teacher') or (/schedule/metadata/@type='group')">
						<tr>
							<xsl:choose>
								<xsl:when test="count(room)>1">
									<xsl:for-each select="room">
										<td id="room-img">
											<i class="icon-location"/>
										</td>
										<td id="room">
											<xsl:choose>
												<xsl:when test="/schedule/@show-links='true'">
													<a>
														<xsl:attribute name="title">перейти к расписанию на аудиторию</xsl:attribute>
														<xsl:attribute name="href">schedule?room=<xsl:value-of select="room/@id"/><xsl:call-template name="append-week-param"/></xsl:attribute>
														<xsl:value-of select="display-name"/>
													</a>
												</xsl:when>
												<xsl:otherwise>
													<xsl:value-of select="display-name"/>
												</xsl:otherwise>
											</xsl:choose>
										</td>
									</xsl:for-each>
								</xsl:when>
								<xsl:otherwise>
									<td id="room-img">
										<i class="icon-location"/>
									</td>
									<td id="room">
										<xsl:choose>
											<xsl:when test="/schedule/@show-links='true'">
												<a>
													<xsl:attribute name="title">перейти к расписанию на аудиторию
                                                    </xsl:attribute>
													<xsl:attribute name="href">schedule?room=<xsl:value-of select="room/@id"/><xsl:call-template name="append-week-param"/></xsl:attribute>
													<xsl:value-of select="room/display-name"/>
												</a>
											</xsl:when>
											<xsl:otherwise>
												<xsl:value-of select="room/display-name"/>
											</xsl:otherwise>
										</xsl:choose>
									</td>
								</xsl:otherwise>
							</xsl:choose>
						</tr>
					</xsl:if>
					<xsl:if test="(/schedule/metadata/@type='room') or (/schedule/metadata/@type='group')">
						<tr>
							<td id="teacher-img">
								<i class="icon-user"/>
							</td>
							<td id="teacher">
								<a id="schedule-link">
									<xsl:attribute name="title"><xsl:value-of select="teacher/@full-name"/></xsl:attribute>
									<xsl:attribute name="href">schedule?employee=<xsl:value-of select="teacher/@id"/><xsl:call-template name="append-week-param"/></xsl:attribute>
									<xsl:value-of select="teacher/display-name"/>
								</a>
							</td>
						</tr>
						<xsl:if test="(/schedule/metadata/@type='group')">
							<!--For group show its subgroup/subgroups. Kind of @one-of-multiple, but several subgroups in a single cell-->
							<tr>
								<xsl:if test="(count(groups/group[@main='true'])=1) and (count(groups/group[@all='true'])=0)">
									<!--Show subgroup only if this is the single group in the group list, and it is not the ALL group, so the student needs to know its a subgroup. So if there's single main group and ZERO 'all' group, so means its a subgroup-->
									<!--Second case: two subgroups of a same group is at the same room. So this is fine, we consider this as a single lesson, so skip this IF.-->
									<td id="group-img" valign="top">
										<i class="icon-users"/>
									</td>
									<td id="subgroup">
										<xsl:for-each select="groups/group">
											<xsl:if test="@main='true'">
												<xsl:call-template name="group"/>
											</xsl:if>
										</xsl:for-each>
									</td>
								</xsl:if>
							</tr>
						</xsl:if>
					</xsl:if>
					<xsl:if test="(/schedule/metadata/@type='teacher') or (/schedule/metadata/@type='room')">
						<tr>
							<xsl:choose>
								<xsl:when test="count(groups/group)>1">
									<td id="group-img" valign="top">
										<i class="icon-users"/>
									</td>
									<td id="group" colspan="4">
										<xsl:for-each select="groups/group">
											<xsl:call-template name="group"/>
										</xsl:for-each>
									</td>
								</xsl:when>
								<xsl:otherwise>
									<td id="group-img">
										<i class="icon-users"/>
									</td>
									<td id="group" colspan="4">
										<xsl:for-each select="groups/group">
											<xsl:call-template name="group"/>
										</xsl:for-each>
									</td>
								</xsl:otherwise>
							</xsl:choose>
						</tr>
					</xsl:if>
				</xsl:otherwise>
			</xsl:choose>
		</table>
	</xsl:template>
	<xsl:template name="subject-cell">
		<xsl:param name="elementNode"/>
		<xsl:choose>
			<xsl:when test="$elementNode/@is-one-of-multiple">
				<xsl:attribute name="id">subject-small</xsl:attribute>
				<xsl:attribute name="align">center</xsl:attribute>
			</xsl:when>
			<xsl:otherwise>
                <xsl:attribute name="id">subject</xsl:attribute>
			</xsl:otherwise>
		</xsl:choose>
        <xsl:choose>
            <xsl:when test="$elementNode/subject[1]/@ikt">
                <a id="schedule-link">
                    <xsl:attribute name="href">https://pns.hneu.edu.ua/course/view.php?id=<xsl:value-of select="$elementNode/subject[1]/@ikt"/></xsl:attribute>
                    <xsl:value-of select="$elementNode/subject"/>
                </a>
            </xsl:when>
            <xsl:otherwise>
                <xsl:value-of select="$elementNode/subject"/>
            </xsl:otherwise>
        </xsl:choose>
 	</xsl:template>
	<xsl:template name="type-cell">
		<xsl:param name="elementNode"/>
		<xsl:choose>
			<xsl:when test="$elementNode/@is-one-of-multiple">
				<xsl:attribute name="id"><xsl:choose><xsl:when test="is-session">attestationType-small</xsl:when><xsl:otherwise>lessonType-small</xsl:otherwise></xsl:choose></xsl:attribute>
			</xsl:when>
			<xsl:otherwise>
				<xsl:attribute name="id"><xsl:choose><xsl:when test="is-session">attestationType</xsl:when><xsl:otherwise>lessonType</xsl:otherwise></xsl:choose></xsl:attribute>
			</xsl:otherwise>
		</xsl:choose>
		<xsl:value-of select="$elementNode/type"/>
	</xsl:template>
	<xsl:template match="group" name="group">
		<xsl:choose>
			<xsl:when test="(/schedule/metadata/@type='teacher') or (/schedule/metadata/@type='room')">
				<!-- title over a group needs to be shown only for teacher or room, as soon as the student knows his group's additional data -->
				<a id="schedule-link">
					<xsl:attribute name="title"><xsl:value-of select="title"/></xsl:attribute>
					<xsl:attribute name="href">schedule?group=<xsl:value-of select="@id"/><xsl:call-template name="append-week-param"/></xsl:attribute>
					<xsl:value-of select="display-name"/>
				</a>
				<br/>
			</xsl:when>
			<xsl:otherwise>
				<span>
					<xsl:value-of select="display-name"/>
				</span>
				<br/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template match="type"/>
	<xsl:template match="week"/>
	<xsl:template name="append-week-param">&amp;week=<xsl:value-of select="/schedule/week/@number"/>
	</xsl:template>
	<xsl:template match="room"/>
	<xsl:template match="teacher"/>
</xsl:stylesheet>
