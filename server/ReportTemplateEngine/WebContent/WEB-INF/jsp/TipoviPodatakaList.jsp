
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
