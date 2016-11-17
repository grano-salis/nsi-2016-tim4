<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Elektrotehnički fakultet</title>

    <!-- Bootstrap -->
    <link href="../vendors/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">    <!-- NProgress -->
    <!-- iCheck -->
    <link href="../vendors/iCheck/skins/flat/green.css" rel="stylesheet">
    <!-- Datatables -->
    <link href="../vendors/datatables.net-bs/css/dataTables.bootstrap.min.css" rel="stylesheet">
    <link href="../vendors/datatables.net-fixedheader-bs/css/fixedHeader.bootstrap.min.css" rel="stylesheet">
    <link href="../vendors/datatables.net-responsive-bs/css/responsive.bootstrap.min.css" rel="stylesheet">

    <!-- Custom Theme Style -->
    <link href="../build/css/custom.min.css" rel="stylesheet">
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

            <!-- menu profile quick info -->
            <div class="profile">
              <div class="profile_pic">
                <img src="images/img.jpg" alt="..." class="img-circle profile_img">
              </div>
              <div class="profile_info">
                <span>Welcome,</span>
                <h2>John Doe</h2>
              </div>
            </div>
            <!-- /menu profile quick info -->

            <br />

            <!-- sidebar menu -->
            <div id="sidebar-menu" class="main_menu_side hidden-print main_menu">
              <div class="menu_section">
                <h3>Meni</h3>
                <ul class="nav side-menu">
                  <li><a><i class="fa fa-file-text-o"></i> Dokumenti <span class="fa fa-chevron-down"></span></a>
                    <ul class="nav child_menu">
                      <li><a href="#">Pregled dokumenata</a></li>
                      <li><a href="#">Dodaj novi dokument</a></li>
                    </ul>
                  </li>
                  <li><a><i class="fa fa-pencil"></i> Tipovi podataka <span class="fa fa-chevron-down"></span></a>
                    <ul class="nav child_menu">
                      <li><a href="#">Pregled tipova podataka</a></li>
                      <li><a href="#">Dodaj novi tip podataka</a></li>
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
                    <img src="images/img.jpg" alt="">John Doe
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
                <h3>Elektrotehnički fakultet <br/><small> Univerziteta u Sarajevu</h3>
              </div>

          <br/>
            </div>

            <div class="clearfix"></div>

            <div class="row">
            

              <div class="col-md-12 col-sm-12 col-xs-12">
                <div class="x_panel">
                  <div class="x_title">
                    <h2>Tipovi podataka</h2>
                    <ul class="nav navbar-right panel_toolbox">
                      <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                      </li>
                   
                      <li><a class="close-link"><i class="fa fa-close"></i></a>
                      </li>
                    </ul>
                    <div class="clearfix"></div>
                  </div>
                  <div class="x_content">
                    
                    <table id="datatable-checkbox" class="table table-striped table-bordered bulk_action" style="width:80%; margin-left:10%;" >
                      <thead>
                        <tr>
                          <th width="8%;"   style="margin-right:4%;" ><input type="checkbox" id="check-all" class="flat"></th>
                          <th width="25%;" style="padding-left:3%; text-align:left;">Naziv</th>
                          <th width="40%;" style="padding-left:3%; text-align:left;">Regex</th>
                          <th width="25%;" style="text-align:center;">Akcije</th>
                        </tr>
                      </thead>


                      <tbody>
                        <tr>
                          <td><input type="checkbox" class="flat" name="table_records"></td>
                          <td style="padding-left:3%; text-align:left;">Datum</td>
                          <td style="padding-left:3%; text-align:left;">^(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](19|20)\d\d$</td>
                          <td style="text-align:center">
                          <a style="cursor:pointer; margin-right:5%;"><i class="fa fa-pencil"></i> </a>
                          <a style="cursor:pointer; color:red; margin-left:5%;"><i class="fa fa-trash"></i> </a>
                          </td>
                          </tr>
                        <tr>
                          <td><input type="checkbox" class="flat" name="table_records"></td>
                          <td style="padding-left:3%; text-align:left;">Cijeli broj</td>
                          <td style="padding-left:3%; text-align:left;">^\s*-?[0-9]{1,10}\s$\</td>
                          <td style="text-align: center;">
                            <a style="cursor:pointer; margin-right:5%;"><i class="fa fa-pencil"></i> </a>
                            <a style="cursor:pointer; color:red; margin-left:5%;"><i class="fa fa-trash"></i> </a>
                          </td>
                        </tr>
        
                      </tbody>
                    </table>
                  </div>
                </div>
              </div>

     
            </div>
          </div>
        </div>
        <!-- /page content -->

        <!-- footer content -->
        <footer>
            <div> ©Elektrotehnički fakultet <a href="#">Tim Delta</a>
          </div>
          
          <div class="clearfix"></div>
        </footer>
        <!-- /footer content -->
      </div>
    </div>

    <!-- jQuery -->
    <script src="../vendors/jquery/dist/jquery.min.js"></script>
    <!-- Bootstrap -->
    <script src="../vendors/bootstrap/dist/js/bootstrap.min.js"></script>
    <!-- FastClick -->
    <script src="../vendors/fastclick/lib/fastclick.js"></script>
    <!-- NProgress -->    <!-- iCheck -->
    <script src="../vendors/iCheck/icheck.min.js"></script>
    <!-- Datatables -->
    <script src="../vendors/datatables.net-bs/js/dataTables.bootstrap.min.js"></script>
    <script src="../vendors/datatables.net-buttons-bs/js/buttons.bootstrap.min.js"></script>
    <script src="../vendors/datatables.net-buttons/js/buttons.html5.min.js"></script>
    <script src="../vendors/datatables.net-fixedheader/js/dataTables.fixedHeader.min.js"></script>
    <script src="../vendors/datatables.net-keytable/js/dataTables.keyTable.min.js"></script>
    <script src="../vendors/datatables.net-responsive-bs/js/responsive.bootstrap.js"></script>

    <!-- Custom Theme Scripts -->
    <script src="../build/js/custom.min.js"></script>

    <!-- Datatables -->

  </body>
</html>