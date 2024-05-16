
export const Dashboard = () => {
  return (
    <>
        <div className="container-fluid px-4">
            <h1 className="mt-4">¡Bienvenido a ChessTournaments!</h1>
            <ol className="breadcrumb mb-4">
                <li className="breadcrumb-item active">Chess Tournaments</li>
            </ol>
            
            <div className="card border-primary mb-3 ">
                <h5 className="card-header text-bg-primary">Torneos más próximos</h5>
                <div className="card-body">
                    <p className="card-text">Torneo 1 Fecha: 14/04/2024 Hora: 17:30</p>
                    <p className="card-text">Torneo 2 Fecha: 15/04/2024 Hora: 16:30</p>
                    <p className="card-text">Torneo 3 Fecha: 23/04/2024 Hora: 18:30</p>
                </div>
            </div>

            <div className="card border-success mb-3">
                <h5 className="card-header text-bg-success">¡No olvides seguirnos en nuestras redes!</h5>
                <div className="card-body">
                    <div className="row">
                        <div className="col">
                            <h5>Twitter</h5>
                        </div>
                        <div className="col">
                            <h5>Instagram</h5>
                        </div>
                        <div className="col">
                            <h5>Tik Tok</h5>
                        </div>
                        <div className="col">
                            <h5>Threads</h5>
                        </div>
                        
                    </div>
                </div>
            </div>

            <div className="card border-warning">
                <h5 className="card-header text-bg-warning text-white">Sobre nosotros</h5>
                <div className="card-body">
                    <h5 className="card-title">¡Conocenos!</h5>
                    <p className="card-text">
                        Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, 
                        totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. 
                        Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, 
                        sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. 
                        Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, 
                        sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. 
                        Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, 
                        nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate 
                        velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?
                        </p>
                </div>
            </div>
            
        </div>
    </>
  )
}
