//
//  ViewController.swift
//  Kadri's Second iOS App
//
//  Created by Kadri Isakar on 11.12.2021.
//

import GliaWidgets
import SalemoveSDK
import UIKit

class ViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
    }


    @IBAction func chat_button(_ sender: Any) {
        startEngagement(.chat)
    }
    
    @IBAction func audio_button(_ sender: Any) {
        startEngagement(.audioCall)
    }
    
    @IBAction func video_button(_ sender: Any) {
        startEngagement(.videoCall)
    }

}

extension ViewController {

    func startEngagement(_ kind: EngagementKind) {
        let myconfiguration = Configuration(
                        appToken: "xWa2EKJTN35En26o",
                        environment: .beta,
                        site: "72c76913-ff1b-48a6-b209-bbeb57bd8649"
                    )
        // Supply the context that will be shown in the CoBrowsing area
        let context = VisitorContext(type: .page, url: "https://kadriiiii.github.io/scram/dc-media-and-giraffes.html/")
        do {
            try Glia.sharedInstance.start(
                kind,
                configuration: myconfiguration,
                queueID: "116c746c-1a62-4883-a80b-01cfb2562c65",
                visitorContext: context
            )
        } catch {
            // Handle error
        }
    }
}
    
