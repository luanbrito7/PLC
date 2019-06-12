import Control.Concurrent.STM
import Control.Concurrent

main :: IO ()
main = do
    lista <- atomically (newBuffer)
    fim <- atomically (newTVar 2)
    forkIO (incrementador lista 1000 fim)
    forkIO (decrementador lista 1000 fim)
    forkIO (waitThreads fim)
    f <- atomically (readTVar fim)
    print f
    return ()

    
type Buffer t = TVar [t]

incrementador :: Buffer Integer -> Integer -> TVar Integer -> IO ()
incrementador buffer 0 fim = atomically (do{
                                                f <- readTVar fim;
                                                writeTVar fim (f-1);
                                           }
                                        )
incrementador buffer num fim = do atomically(do{ put buffer num})
                                  incrementador buffer (num-1) fim

decrementador :: Buffer Integer -> Integer -> TVar Integer -> IO ()
decrementador buffer 0 fim = atomically (do {
                                                f <- readTVar fim;
                                                writeTVar fim (f-1);
                                            }
                                        )
decrementador buffer num fim = do atomically(do{  valor <- get buffer;
                                                  return ()
                                               }
                                            )   
                                  decrementador buffer (num-1) fim

waitThreads :: TVar Integer -> IO ()
waitThreads fim = atomically(do {
                                    v <- readTVar fim;
                                    if v > 0
                                        then retry
                                        else return ()
                                }
                            )
    
newBuffer :: STM (Buffer t)
newBuffer = newTVar []

get :: Buffer t -> STM (t)
get mem = do valor <- readTVar mem
             if null valor
                then retry
             else do 
                writeTVar mem $ tail valor
                return $ head valor

put :: Buffer t -> t -> STM ()
put mem value = do  lista <- readTVar mem
                    writeTVar mem $ lista ++ [value]