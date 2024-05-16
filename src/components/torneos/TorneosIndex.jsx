
export const TorneosIndex = () => {
  return (

    <>
        <div className="container-fluid px-4">
            
            <h1 className="mt-4">Lista de torneos</h1>
            <ol className="breadcrumb mb-4">
                <li className="breadcrumb-item active">Torneos</li>
            </ol>
            <div>
                <a href="/salazoom/create" className="btn btn-success mb-4 fs-6"> + Nueva Sala </a>
            </div>
            <div className="card mb-4">
                <div className="card-header">
                    <i className="fas fa-crown me-1"></i>
                    Torneos
                </div>
                <div className="card-body">
                    <table id="datatablesSimple" className="table table-striped">
                        <thead>
                            <tr>
                                <th scope="col">Nombre</th>
                                <th scope="col">Código</th>
                                <th className="d-flex justify-content-end">Acciones</th>

                            </tr>
                        </thead>
                        <tfoot>
                            <tr>
                                <th>Nombre</th>
                                <th>Código</th>
                                <th className="d-flex justify-content-end">Acciones</th>
                            </tr>
                        </tfoot>
                        <tbody>
                        
                        </tbody>
                    </table>

                </div>
            </div>
        </div>  
    </>

        )
}
