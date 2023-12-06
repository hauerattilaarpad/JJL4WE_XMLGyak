<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet xmlns:xsl = "http://www.w3.org/1999/XSL/Transform" version = "1.0" >

	<xsl:template match="/">
		<html>
			<body>

				<table style="border-collapse: collapse;">
					<tr style="background-color: greenyellow;">
						<th>ID</th>
						<th>Tipus</th>
						<th>Targy</th>
						<th>Idopont</th>
						<th>Helyszin</th>
						<th>Oktato</th>
						<th>Szak</th>
					</tr>
					
					<xsl:for-each select="ZUYISF_orarend/ora">
						<tr>

							<td><xsl:value-of select = "@id"/></td>
							<td><xsl:value-of select = "@tipus"/></td>
							<td><xsl:value-of select = "targy"/></td>

							<td><xsl:value-of select = "idopont/nap"/></td>
							<td><xsl:value-of select = "idopont/tol"/></td>
                            <td><xsl:value-of select = "idopont/ig"/></td>

							<td><xsl:value-of select = "helyszin"/></td>
							<td><xsl:value-of select = "oktato"/></td>
							<td><xsl:value-of select = "szak"/></td>

						</tr>
					</xsl:for-each>
					
				</table>
			</body>
		</html>
		
	</xsl:template>	
	<xsl:output method="html" version="5.0" encoding="utf-8" indent="yes"></xsl:output>

</xsl:stylesheet>