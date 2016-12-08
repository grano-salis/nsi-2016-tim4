<!DOCTYPE html>
<html>
	<head>
		<META HTTP-EQUIV="content-type" CONTENT="text/html; charset=utf-8">
    	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    	<meta name="viewport" content="width=device-width, initial-scale=1">
		
		<title>Report template engine</title>
		
		<link href="/ReportTemplateEngine/css/bootstrap.min.css" rel="stylesheet">
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">    <!-- NProgress -->
		<link href="/ReportTemplateEngine/css/green.css" rel="stylesheet">
		<link href="/ReportTemplateEngine/css/dataTables.bootstrap.min.css" rel="stylesheet">
		<link href="/ReportTemplateEngine/css/fixedHeader.bootstrap.min.css" rel="stylesheet">
		<link href="/ReportTemplateEngine/css/responsive.bootstrap.min.css" rel="stylesheet">
		<link href="/ReportTemplateEngine/css/custom.min.css" rel="stylesheet">
	</head>
	
<body class="nav-md">
    <div class="container body">
      <div class="main_container">
        <div class="col-md-3 left_col">
          <div class="left_col scroll-view">
            <div class="navbar nav_title" style="border: 0;">
              <a href="#" class="site_title"><i class="fa fa-book"></i> <span>ETF Sarajevo</span></a>
            </div>

            <div class="clearfix"></div>

            <div class="profile">
              <div class="profile_pic">
                <img src="/ReportTemplateEngine/img/user.png" alt="..." class="img-circle profile_img">
              </div>
              <div class="profile_info">
                <span>Welcome,</span>
                <h2>John Doe</h2>
              </div>
            </div>
            
            <br><br><br>
			
            <div id="sidebar-menu" class="main_menu_side hidden-print main_menu">
              <div class="menu_section">
				<br>
                <ul class="nav side-menu">
                  <li><a><i class="fa fa-file-text-o"></i> Dokumenti <span class="fa fa-chevron-down"></span></a>
                    <ul class="nav child_menu">
                      <li><a href="#" class="documents" name="list">Pregled dokumenata</a></li>
                      <li><a href="#" class="documents" name="add">Dodaj novi dokument</a></li>
                    </ul>
                  </li>
                  <li><a><i class="fa fa-pencil"></i> Tipovi podataka <span class="fa fa-chevron-down"></span></a>
                    <ul class="nav child_menu">
                      <li><a href="#" class="templates" name="list">Pregled tipova podataka</a></li>
                      <li><a href="#" class="templates" name="add">Dodaj novi tip podataka</a></li>
                    </ul>
                  </li>
             
                </ul>
              </div>
            </div>
          </div>
        </div>

        <!-- top navigation -->
        <div class="top_nav">
          <div class="nav_menu">
            <nav>
              <div class="nav toggle">
                <a id="menu_toggle"><i class="fa fa-bars"></i></a>
              </div>

              <ul class="nav navbar-nav navbar-right">
                <li class="">
                  <a href="javascript:;" class="user-profile dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
                    <img src="/ReportTemplateEngine/img/user.png" alt="">John Doe
                    <span class=" fa fa-angle-down"></span>
                  </a>
                  <ul class="dropdown-menu dropdown-usermenu pull-right">
                    <li><a href="#"> Profil</a></li>
                    <li><a href="#">Promijeni password </a></li>
                    <li><a href="#">Pomoć</a></li>
                    <li><a href="#"><i class="fa fa-sign-out pull-right"></i>Odjavi se</a></li>
                  </ul>
                </li>

              </ul>
            </nav>
          </div>
        </div>
        <!-- /top navigation -->

        <!-- page content -->
        <div class="right_col" role="main">
          <div class="">
            <div class="page-title">
              <div class="title_left">
                <h3>Elektrotehnicki fakultet <br/> Univerziteta u Sarajevu</h3>
              </div>

          <br/>
            </div>

            <div class="clearfix"></div>

			<div class="ajax-page-content">
			
			</div>

        </div>
        </div>

        <!-- footer content -->
        <footer>
            <div> Elektrotehnicki fakultet <a href="#">Tim Delta</a>
          </div>
          
          <div class="clearfix"></div>
        </footer>
        <!-- /footer content -->
      </div>
    </div>
		<script src="/ReportTemplateEngine/js/jquery-2.1.3.min.js"></script>
		<script src="/ReportTemplateEngine/js/bootstrap.min.js"></script>
		<script src="/ReportTemplateEngine/js/icheck.min.js"></script>
		<script src="/ReportTemplateEngine/js/dataTables.bootstrap.min.js"></script>
		<script src="/ReportTemplateEngine/js/dataTables.keyTable.min.js"></script>
		<script src="/ReportTemplateEngine/js/responsive.bootstrap.js"></script>
		<script src="/ReportTemplateEngine/js/custom.min.js"></script>
		<script src="/ReportTemplateEngine/js/singlePaging.js"></script>
		<script src="/ReportTemplateEngine/js/templates.js"></script>
	</body>
</html>